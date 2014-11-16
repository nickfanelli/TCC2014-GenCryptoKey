package br.pucc.engComp.GenCryptoKey.models;

import br.pucc.engComp.GenCryptoKey.controller.DB;
import br.pucc.engComp.GenCryptoKey.controller.SettingsPOJO;

import java.sql.*;
import java.util.ArrayList;

public class SettingsDAO {

    private SettingsDAO(){}

    // Check whether the desired setting exists and is registered on the database
    public static boolean isRegistered(int parameterID) throws Exception {

        PreparedStatement ps = DB.getPreparedStatement("SELECT * FROM GASETTINGS WHERE ID = ?");
        ps.setInt(1, parameterID);

        ResultSet rs = ps.executeQuery();
        boolean ans = rs.next();

        rs.close();
        ps.close();

        return ans;
    }

    public static void validateAttributes(SettingsPOJO parameter) throws Exception {
        if (parameter == null)
            throw new Exception ("Parameter not given.");

        if (!isRegistered(parameter.getSettingsID()))
            throw new Exception ("Parameter not registered.");
    }

    // Insert new parameter
    public static int newSettings(SettingsPOJO parameter) throws Exception {

        PreparedStatement ps = DB.getPreparedStatement("INSERT INTO GASETTINGS (INDIVIDUALSIZE, POPULATIONSIZE, "
                + "CROSSOVERPOINTS, MUTATIONSPERINDIVIDUAL, MUTATIONRATE, PERCENTAGEOFINDIVIDUALSTOCROSS, "
                + "MAXIMUMPOPULATIONSIZE, GENERATIONSTOSTOP, SCHEDULEDKEYGENERATION, "
                + "SCHEDULEDKEYGENERATIONTIME, WRITELOG) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        ps.setInt(1, parameter.getIndividualSize());
        ps.setInt(2, parameter.getInitialPopulationSize());
        ps.setInt(3, parameter.getNumOfCrossoverPoints());
        ps.setInt(4, parameter.getNumOfMutationsPerIndividual());
        ps.setDouble(5, parameter.getMutationRate());
        ps.setDouble(6, parameter.getPercentageOfIndividualsToCross());
        ps.setInt(7, parameter.getMaxPopulationSize());
        ps.setInt(8, parameter.getMaxGenerationsToStop());
        ps.setBoolean(9, parameter.isScheduledKeyGeneration());
        ps.setInt(10, parameter.getScheduledKeyGenerationTime());
        ps.setBoolean(11, parameter.isWriteLogActive());

        int result = ps.executeUpdate();

        ps.close();

        return result;
    }

    // Remove parameter from database
    public static void deleteParameter(int parameterID) throws Exception {
        if (!isRegistered(parameterID))
            throw new Exception ("Parameter not registered.");

        PreparedStatement ps = DB.getPreparedStatement("DELETE FROM GASETTINGS WHERE ID = ?");
        ps.setInt(1, parameterID);

        ps.executeUpdate();
    }

    // Update parameter's personal information
    public static int updateParameter(SettingsPOJO parameter) throws Exception {
        validateAttributes(parameter);

        PreparedStatement ps = DB.getPreparedStatement("UPDATE GASETTINGS SET INDIVIDUALSIZE = ?,"
                + " POPULATIONSIZE = ?, CROSSOVERPOINTS = ?, MUTATIONSPERINDIVIDUAL = ?, MUTATIONRATE = ?,"
                + " PERCENTAGEOFINDIVIDUALSTOCROSS = ?, MAXIMUMPOPULATIONSIZE = ?,"
                + " GENERATIONSTOSTOP = ?, SCHEDULEDKEYGENERATION = ?, WRITELOG = ?,"
                + " WHERE ID = ?");

        ps.setInt(1, parameter.getIndividualSize());
        ps.setInt(2, parameter.getInitialPopulationSize());
        ps.setInt(3, parameter.getNumOfCrossoverPoints());
        ps.setInt(4, parameter.getNumOfMutationsPerIndividual());
        ps.setDouble(5, parameter.getMutationRate());
        ps.setDouble(6, parameter.getPercentageOfIndividualsToCross());
        ps.setInt(7, parameter.getMaxPopulationSize());
        ps.setInt(8, parameter.getMaxGenerationsToStop());
        ps.setBoolean(9, parameter.isScheduledKeyGeneration());
        ps.setInt(10, parameter.getScheduledKeyGenerationTime());
        ps.setBoolean(11, parameter.isWriteLogActive());

        int result = ps.executeUpdate();

        ps.close();

        return result;
    }

    public static SettingsPOJO getSettingsProfileByID(int settingsProfileID) throws Exception {

        PreparedStatement ps = DB.getPreparedStatement("SELECT * FROM GASETTINGS WHERE ID = ?");
        ps.setInt(1, settingsProfileID);

        ResultSet rs = ps.executeQuery();

        if (!rs.first())
            throw new Exception ("Parameter not registered.");

        SettingsPOJO parameter;
        parameter = new SettingsPOJO (rs.getInt("ID"),
                rs.getInt("INDIVIDUALSIZE"),
                rs.getInt("POPULATIONSIZE"),
                rs.getInt("CROSSOVERPOINTS"),
                rs.getInt("MUTATIONSPERINDIVIDUAL"),
                rs.getDouble("MUTATIONRATE"),
                rs.getDouble("PERCENTAGEOFINDIVIDUALSTOCROSS"),
                rs.getInt("MAXIMUMPOPULATIONSIZE"),
                rs.getInt("GENERATIONSTOSTOP"),
                rs.getBoolean("SCHEDULEDKEYGENERATION"),
                rs.getInt("SCHEDULEDKEYGENERATIONTIME"),
                rs.getBoolean("WRITELOG"));

        rs.close();
        ps.close();

        return parameter;
    }

    public static ArrayList<SettingsPOJO> getAllSettingsProfiles() throws Exception {
        ArrayList<SettingsPOJO> settingsList = null;

        PreparedStatement ps = DB.getPreparedStatement("SELECT * FROM GASETTINGS");
        ResultSet rs = ps.executeQuery();

        if(rs != null) {
            settingsList = new ArrayList<SettingsPOJO>();
            SettingsPOJO settings = null;

            while(rs.next()){
                settings = new SettingsPOJO();

                settings.setSettingsID(rs.getInt("ID"));
                settings.setIndividualSize(rs.getInt("INDIVIDUALSIZE"));
                settings.setInitialPopulationSize(rs.getInt("POPULATIONSIZE"));
                settings.setNumOfCrossoverPoints(rs.getInt("CROSSOVERPOINTS"));
                settings.setNumOfMutationsPerIndividual(rs.getInt("MUTATIONSPERINDIVIDUAL"));
                settings.setMutationRate(rs.getDouble("MUTATIONRATE"));
                settings.setPercentageOfIndividualsToCross(rs.getDouble("PERCENTAGEOFINDIVIDUALSTOCROSS"));
                settings.setMaxPopulationSize(rs.getInt("MAXIMUMPOPULATIONSIZE"));
                settings.setMaxGenerationsToStop(rs.getInt("GENERATIONSTOSTOP"));
                settings.setScheduledKeyGeneration(rs.getBoolean("SCHEDULEDKEYGENERATION"));
                settings.setScheduledKeyGenerationTime(rs.getInt("SCHEDULEDKEYGENERATIONTIME"));
                settings.setWriteLogActive(rs.getBoolean("WRITELOG"));

                settingsList.add(settings);
            }
            rs.close();
            ps.close();
        }
        return settingsList;
    }
}
