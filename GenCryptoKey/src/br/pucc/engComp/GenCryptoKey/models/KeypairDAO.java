package br.pucc.engComp.GenCryptoKey.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import br.pucc.engComp.GenCryptoKey.controller.DB;
import br.pucc.engComp.GenCryptoKey.controller.KeypairPOJO;

public class KeypairDAO {

    private KeypairDAO(){}

    // Check whether user is registered on the database
    public static boolean isRegistered(KeypairPOJO keypair) throws Exception {

        PreparedStatement ps = DB.getPreparedStatement("SELECT * FROM GENERATEDKEYS WHERE GENERATIONTIMESTAMP = ?");
        ps.setTimestamp(1, keypair.getGenerationTimestamp());

        ResultSet rs = ps.executeQuery();
        boolean ans = rs.next();

        rs.close();
        ps.close();

        return ans;
    }

    public static void validateAttributes(KeypairPOJO keypair) throws Exception {
        if (keypair == null)
            throw new Exception ("Keypair not given.");

        if (!isRegistered(keypair))
            throw new Exception ("Keypair not registered.");
    }

    // Insert new user
    public static int newKeypair(KeypairPOJO keypair) throws Exception {
        if (keypair == null)
            throw new Exception ("Keypair not given.");

        int result;

        PreparedStatement ps = DB.getPreparedStatement("INSERT INTO GENERATEDKEYS (GENERATEDKEY, PUBLICEXPONENT, PRIVATEEXPONENT, MODULUS, KEYPAIRDESCRIPTION, GENERATIONTIMESTAMP)"
                + " VALUES (?, ?, ?, ?, ?, ?)");

        ps.setString(1, keypair.getGeneratedKeyBase64());
        ps.setString(2, keypair.getPublicExponent());
        ps.setString(3, keypair.getPrivateExponent());
        ps.setString(4, keypair.getModulus());
        ps.setString(5, keypair.getKeypairDescription());
        ps.setTimestamp(6, keypair.getGenerationTimestamp());

        result = ps.executeUpdate();
        ps.close();

        return result;
    }

    // Remove user from database
    public static int deleteKeypair(KeypairPOJO keypair) throws Exception {
        if (!isRegistered(keypair))
            throw new Exception ("Keypair not registered.");

        int result;

        PreparedStatement ps = DB.getPreparedStatement("DELETE FROM GENERATEDKEYS WHERE ID = ?");
        ps.setInt(1,keypair.getKeypairID());
        result = ps.executeUpdate();
        ps.close();

        return result;
    }

    // Update user's personal information
    public static int updateKeyPair(KeypairPOJO keypair) throws Exception {
        int result;

        PreparedStatement ps = DB.getPreparedStatement("UPDATE GENERATEDKEYS SET GENERATEDKEY = ?, "
                + "PUBLICEXPONENT = ?, "
                + "PRIVATEEXPONENT = ?, "
                + "MODULUS = ?, "
                + "KEYPAIRDESCRIPTION = ?, "
                + "GENERATIONTIMESTAMP = ? "
                + "WHERE ID = ?");

        ps.setString(1, keypair.getGeneratedKeyBase64());
        ps.setString(2, keypair.getPublicExponent());
        ps.setString(3, keypair.getPrivateExponent());
        ps.setString(4, keypair.getModulus());
        ps.setString(5, keypair.getKeypairDescription());
        ps.setTimestamp(6, keypair.getGenerationTimestamp());
        ps.setInt(7, keypair.getKeypairID());

        result = ps.executeUpdate();
        ps.close();

        return result;
    }

    public static KeypairPOJO getKeypair(int keypairID) throws Exception {
        KeypairPOJO keypair = null;

        PreparedStatement ps = DB.getPreparedStatement("SELECT * FROM GENERATEDKEYS WHERE ID = ?");
        ps.setInt(1, keypairID);

        ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            rs.close();
            ps.close();
            throw new Exception ("Keypair not registered.");
        }
        Calendar generationTimestamp = Calendar.getInstance();
        generationTimestamp.setTimeInMillis(rs.getTimestamp("GENERATIONTIMESTAMP").getTime());

        keypair = new KeypairPOJO (rs.getInt("ID"),
                rs.getString("GENERATEDKEY"),
                rs.getString("PUBLICEXPONENT"),
                rs.getString("PRIVATEEXPONENT"),
                rs.getString("MODULUS"),
                rs.getString("KEYPAIRDESCRIPTION"),
                generationTimestamp);

        rs.close();
        ps.close();

        return keypair;
    }

    public static KeypairPOJO getKeypairByDescription(String keypairDescription) throws Exception {
        KeypairPOJO keypair = null;

        PreparedStatement ps = DB.getPreparedStatement("SELECT * FROM GENERATEDKEYS WHERE KEYPAIRDESCRIPTION = ?");
        ps.setString(1, keypairDescription);

        ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            rs.close();
            ps.close();
            throw new Exception ("Keypair not registered.");
        }
        Calendar generationTimestamp = Calendar.getInstance();
        generationTimestamp.setTimeInMillis(rs.getTimestamp("GENERATIONTIMESTAMP").getTime());

        keypair = new KeypairPOJO (rs.getInt("ID"),
                rs.getString("GENERATEDKEY"),
                rs.getString("PUBLICEXPONENT"),
                rs.getString("PRIVATEEXPONENT"),
                rs.getString("MODULUS"),
                rs.getString("KEYPAIRDESCRIPTION"),
                generationTimestamp);

        rs.close();
        ps.close();

        return keypair;
    }

    public static ArrayList<KeypairPOJO> getKeypairs() throws Exception {
        ArrayList<KeypairPOJO> keypairs = null;
        PreparedStatement ps = DB.getPreparedStatement("SELECT * FROM GENERATEDKEYS");

        ResultSet rs = ps.executeQuery();

        if(rs != null){
            keypairs = new ArrayList<KeypairPOJO>();
            KeypairPOJO keypair = null;

            Calendar generationTimestamp;

            while(rs.next()){

                generationTimestamp = new GregorianCalendar();

                generationTimestamp.setTimeInMillis(rs.getTimestamp("GENERATIONTIMESTAMP").getTime());

                keypair = new KeypairPOJO();

                keypair.setKeypairID(rs.getInt("ID"));
                keypair.setGeneratedKeyBase64(rs.getString("GENERATEDKEY"));
                keypair.setPublicExponent(rs.getString("PUBLICEXPONENT"));
                keypair.setPrivateExponent(rs.getString("PRIVATEEXPONENT"));
                keypair.setModulus(rs.getString("MODULUS"));
                keypair.setKeypairDescription(rs.getString("KEYPAIRDESCRIPTION"));
                keypair.setGenerationTimestamp(generationTimestamp);

                keypairs.add(keypair);
            }
            rs.close();
        }
        ps.close();
        return keypairs;
    }
}
