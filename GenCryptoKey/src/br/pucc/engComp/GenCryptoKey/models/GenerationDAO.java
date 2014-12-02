package br.pucc.engComp.GenCryptoKey.models;

import br.pucc.engComp.GenCryptoKey.controller.DB;
import br.pucc.engComp.GenCryptoKey.controller.Individual;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;

public class GenerationDAO {

	private GenerationDAO(){}

	// Check whether the desired setting exists and is registered on the database
	public static boolean isRegistered(int generationKeySize) throws Exception {

		PreparedStatement ps = DB.getPreparedStatement("SELECT * FROM PASTGENERATIONS WHERE INDIVIDUALSIZE = ?");
		ps.setInt(1, generationKeySize);

		ResultSet rs = ps.executeQuery();
		boolean ans = rs.next();

		rs.close();
		ps.close();

		return ans;
	}

	// Insert new generation
	public static int newGeneration(ArrayList<Individual> individuals) throws Exception {
		int result = 0;
		if (!isRegistered(individuals.get(0).getN().bitLength())) { // insert if there is no generation with this key size
			PreparedStatement ps = DB.getPreparedStatement("INSERT INTO PASTGENERATIONS (INDIVIDUALSIZE, PRIMEONE, PRIMETWO, PUBLICEXPONENT)"
					+ " VALUES (?, ?, ?, ?)");

			for(Individual individual : individuals) {
				ps.setInt(1, individual.getN().bitLength());
				ps.setString(2, individual.getP().toString());
				ps.setString(3, individual.getQ().toString());
				ps.setInt(4, individual.getE().intValue()); // This is an INT because the Public Exponent value is constant at 65537. Otherwise, use you String.

				result = ps.executeUpdate();
			}

			ps.close();
		} else { // if there is, then just update the existing generation's data
			PreparedStatement ps = DB.getPreparedStatement("UPDATE PASTGENERATIONS SET INDIVIDUALSIZE = ?, PRIMEONE = ?, PRIMETWO = ?, PUBLICEXPONENT = ?");

			for(Individual individual : individuals) {
				ps.setInt(1, individual.getN().bitLength());
				ps.setString(2, individual.getP().toString());
				ps.setString(3, individual.getQ().toString());
				ps.setInt(4, individual.getE().intValue()); // This is an INT because the Public Exponent value is constant at 65537. Otherwise, use you String.

				result = ps.executeUpdate();
			}
		}


		return result;
	}

	// Remove generation with specific key size from database
	public static void deleteGeneration(int generationKeySize) throws Exception {
		if (!isRegistered(generationKeySize))
			throw new Exception ("Generation not registered.");

		PreparedStatement ps = DB.getPreparedStatement("DELETE FROM PASTGENERATIONS WHERE INDIVIDUALSIZE = ?");
		ps.setInt(1, generationKeySize);

		ps.executeUpdate();
	}


	public static ArrayList<Individual> getGenerationByKeySize(int generationKeySize) throws Exception {
		ArrayList<Individual> generation = null;

		PreparedStatement ps = DB.getPreparedStatement("SELECT * FROM PASTGENERATIONS WHERE INDIVIDUALSIZE = ?");
		ps.setInt(1, generationKeySize);

		ResultSet rs = ps.executeQuery();

		if(rs != null) {
			generation = new ArrayList<Individual>();
			Individual individual = null;
			while(rs.next()) {
				individual = new Individual(new BigInteger(rs.getString("PRIMEONE")), new BigInteger(rs.getString("PRIMETWO")));
				generation.add(individual);
			}
		}

		rs.close();
		ps.close();

		return generation;
	}
}

