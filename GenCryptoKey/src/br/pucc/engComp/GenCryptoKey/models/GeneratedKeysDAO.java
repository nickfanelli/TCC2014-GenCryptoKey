package br.pucc.engComp.GenCryptoKey.models;

import java.sql.ResultSet;
import java.util.ArrayList;

import br.pucc.engComp.GenCryptoKey.controller.DB;
import br.pucc.engComp.GenCryptoKey.controller.KeypairPOJO;

public class GeneratedKeysDAO {
	private DB db;
	private static GeneratedKeysDAO instance;

	// Obtaining connection to the database using Singleton
	public static GeneratedKeysDAO getInstance() {
		if(instance == null) {
			try{
				DB db = new DB();
				instance = new GeneratedKeysDAO(db);
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
		return instance;
	}

	public GeneratedKeysDAO (DB db) throws Exception{
		if(instance == null) {
			instance = this;
			if (db == null)
				throw new Exception ("No access to the database.");
			this.db = db;
		} else {
			System.out.println("There already is an existing instance! No cookies for you!");
		}
	}

	// Check whether user is registered on the database
	public boolean isRegistered(KeypairPOJO keypair) throws Exception {
		String query;

		// TODO: Arrumar toda a bagunca nos statements
		// PreparedStatement ps = DB.getConnection().prepareStatement("SELECT * FROM GENERATEDKEYS WHERE GENERATIONTIMESTAMP = ?");
		// ps.setString(1, keypair.getGenerationTimestamp());
		// ps.executeQuery();

		query = "SELECT * FROM GENERATEDKEYS WHERE GENERATIONTIMESTAMP = '" +
				keypair.getGenerationTimestamp() + "'";

		ResultSet rs = db.execQuery (query);

		boolean res = rs.next();
		rs.close();
		return res;
	}

	public void validateAttributes(KeypairPOJO keypair) throws Exception {
		if (keypair == null)
			throw new Exception ("Keypair not given.");

		if (!isRegistered(keypair))
			throw new Exception ("Keypair not registered.");
	}

	// Insert new user
	public int newKeypair(KeypairPOJO keypair) throws Exception {
		if (keypair == null)
			throw new Exception ("Keypair not given.");

		String sqlCmd;

		sqlCmd = "INSERT INTO GENERATEDKEYS (GENERATEDKEY, PUBLICEXPONENT, PRIVATEEXPONENT, MODULUS, GENERATIONTIMESTAMP) VALUES ('" +
				keypair.getGeneratedKeyBase64() + "', '" +
				keypair.getPublicExponent() + "', '" +
				keypair.getPrivateExponent() + "', '" +
				keypair.getModulus() + "', '" +
				keypair.getGenerationTimestamp() + "')";

		try{
			return db.execCommand(sqlCmd);
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception ("This keypair is already registered.");
		}

	}

	// Remove user from database
	public int deleteKeypair(KeypairPOJO keypair) throws Exception {
		if (!isRegistered(keypair))
			throw new Exception ("Keypair not registered.");

		String sqlCmd;

		sqlCmd = "DELETE FROM GENERATEDKEYS WHERE ID =" +
				keypair.getKeypairID();

		return db.execCommand (sqlCmd);
	}

	// Update user's personal information
	public int updateKeyPair(KeypairPOJO keypair) throws Exception {
		String cmdSQL;

		cmdSQL = "UPDATE GENERATEDKEYS SET " +
				"GENERATEDKEY=" +
				"'" + keypair.getGeneratedKeyBase64() + "'" +
				", PUBLICEXPONENT=" +
				"'" + keypair.getPublicExponent() + "'" +
				", PRIVATEEXPONENT=" +
				"'" + keypair.getPrivateExponent() + "'" +
				", MODULUS=" +
				"'" + keypair.getModulus() + "'" +
				", GENERATIONTIMESTAMP=" +
				"'" + keypair.getGenerationTimestamp() + "'" +
				"WHERE ID=" + keypair.getKeypairID();

		return db.execCommand(cmdSQL);
	}

	public KeypairPOJO getKeypair(int keypairID) throws Exception {
		String query;

		query = "SELECT * FROM GENERATEDKEYS WHERE ID=" + keypairID;

		ResultSet rs = db.execQuery (query);

		if (!rs.next())
			throw new Exception ("Keypair not registered.");

		KeypairPOJO keypair;
		keypair = new KeypairPOJO (rs.getInt("ID"),
				rs.getString("GENERATEDKEY"),
				rs.getString("PUBLICEXPONENT"),
				rs.getString("PRIVATEXPONENT"),
				rs.getString("MODULUS"),
				rs.getString("GENERATIONTIMESTAMP"));

		rs.close();
		return keypair;
	}

	public ArrayList<KeypairPOJO> getKeypairs() throws Exception{
		String query;
		ArrayList<KeypairPOJO> keypairs = null;
		query = "SELECT * FROM GENERATEDKEYS";

		ResultSet rs = db.execQuery (query);

		if(rs != null){
			keypairs = new ArrayList<KeypairPOJO>();
			KeypairPOJO keypair = null;

			while(rs.next()){
				keypair = new KeypairPOJO();

				keypair.setKeypairID(rs.getInt("ID"));
				keypair.setGeneratedKeyBase64(rs.getString("GENERATEDKEY"));
				keypair.setPublicExponent(rs.getString("PUBLICEXPONENT"));
				keypair.setPrivateExponent(rs.getString("PRIVATEEXPONENT"));
				keypair.setModulus(rs.getString("MODULUS"));
				keypair.setGenerationTimestamp(rs.getString("GENERATIONTIMESTAMP"));

				keypairs.add(keypair);
			}
			rs.close();
		}
		return keypairs;
	}
}