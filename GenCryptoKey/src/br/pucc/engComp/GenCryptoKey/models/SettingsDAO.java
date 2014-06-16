package br.pucc.engComp.GenCryptoKey.models;

import br.pucc.engComp.GenCryptoKey.controller.DB;
import br.pucc.engComp.GenCryptoKey.controller.SettingsPOJO;

import java.sql.*;
import java.util.ArrayList;

public class SettingsDAO {

		private DB db;
		private static SettingsDAO instance;
		
		// Obtaining connection to the database using Singleton
		public static SettingsDAO getInstance() {
			if(instance == null) {
				try{
					DB db = new DB();
					instance = new SettingsDAO(db);
				}catch(Exception e){
					e.printStackTrace();
					return null;
				}
			}
			return instance;
		}
		
		public SettingsDAO (DB db) throws Exception{
			if(instance == null) {
				instance = this;
				if (db == null)
		            throw new Exception ("No access to the database.");
		        this.db = db;
			} else {
				System.out.println("There already is an existing instance! No cookies for you!");
			}
	    }
		
		// Check whether the desired setting exists and is registered on the database
		public boolean isRegistered(int parameterID) throws Exception
	    {
	        String query;

	        query = "SELECT * FROM GASETTINGS WHERE ID =" +
	        		parameterID;

			/*query = "SELECT COUNT(*) AS howMany " +
	              "FROM GASETTINGS WHERE CODIGO=" +
	              parameterID;*/

	        ResultSet rs = db.execQuery (query);

	        boolean res = rs.first();  
	        rs.close();
	        return res;
	    }
		
		public void valideAttributes(SettingsPOJO parameter) throws Exception{
			if (parameter == null)
	            throw new Exception ("Parameter not given.");

	        if (!isRegistered(parameter.getParameterID()))
	            throw new Exception ("Parameter not registered.");
		}
		
		// Insert new parameter
		public int newSettings(SettingsPOJO parameter) throws Exception{
			
			String sqlCmd;
			
			sqlCmd = "INSERT INTO GASETTINGS (INDIVIDUALSIZE, POPULATIONSIZE, CROSSOVERPOINTS, MUTATIONRATE, "
						+ "PRESERVEDINDIVIDUALS, FITINDIVIDUALSTOSTOP, GENERATIONSTOSTOP, SCHEDULEKEYGENERATION, WRITELOG) "
						+ "VALUES (" + 
						parameter.getIndividualSize() + ", " + 
						parameter.getPopulationSize() + ", " +
						parameter.getNumOfCrossoverPoints() + ", " + 
						parameter.getMutationRate() + ", " + 
						parameter.getMaxPreservedIndividuals() + ", " + 
						parameter.getNumOfFitIndividualsToStop() + ", " + 
						parameter.getMaxGenerationsToStop() + ", " + 
						parameter.isScheduleKeyGeneration() + ", " + 
						parameter.isWriteLog()+ ")";
			try{
				return db.execCommand(sqlCmd);
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception ("Error trying to insert Settings.");
			}
			
		}
		
		// Remove parameter from database
		public void deleteParameter(int parameterID) throws Exception{
	        if (!isRegistered(parameterID))
	            throw new Exception ("Parameter not registered.");

	        String sqlCmd;

	        sqlCmd = "DELETE FROM GASETTINGS WHERE ID =" +
	              parameterID;

	        db.execCommand (sqlCmd);
	    }

		// Update parameter's personal information
	    public int updateParameter(SettingsPOJO parameter) throws Exception {
	    		valideAttributes(parameter);;
	    		String cmdSQL;

		        cmdSQL = "UPDATE GASETTINGS SET " +
				        		"INDIVIDUALSIZE=" +
				        			parameter.getIndividualSize() +
				              ", POPULATIONSIZE=" + 
				              		parameter.getPopulationSize() +
				              ", CROSSOVERPOINTS=" + 
				              		parameter.getNumOfCrossoverPoints() +
				              ", MUTATIONRATE=" + 
				              		parameter.getMutationRate() +
				              ", PRESERVEDINDIVIDUALS=" + 
				              		parameter.getMaxPreservedIndividuals() +
				              ", FITINDIVIDUALSTOSTOP=" + 
				              		parameter.getNumOfFitIndividualsToStop() +
				              ", GENERATIONSTOSTOP=" + 
				              		parameter.getMaxGenerationsToStop() +
				              ", SCHEDULEKEYGENERATION=" + 
				              		parameter.isScheduleKeyGeneration() +
				              ", WRITELOG=" + 
				              		parameter.isWriteLog() +
		              " WHERE ID=" + parameter.getParameterID();

		        return db.execCommand(cmdSQL);
	    }

	    public SettingsPOJO getParameter(int parameterID) throws Exception{
	        String query;

	        query = "SELECT * FROM GASETTINGS WHERE ID=" + parameterID;

	        ResultSet rs = db.execQuery (query);

	        if (!rs.first())
	            throw new Exception ("Parameter not registered.");

	        SettingsPOJO parameter;
	        parameter = new SettingsPOJO (rs.getInt("ID"),
	        				   rs.getInt("INDIVIDUALSIZE"),
	                           rs.getInt("POPULATIONSIZE"),
	                           rs.getInt("CROSSOVERPOINTS"),
	                           rs.getInt("MUTATIONRATE"),
	                           rs.getInt("PRESERVEDINDIVIDUALS"),
	                           rs.getInt("FITINDIVIDUALSTOSTOP"),
	                           rs.getInt("GENERATIONSTOSTOP"),
	                           rs.getBoolean("SCHEDULEKEYGENERATION"),
	                           rs.getBoolean("WRITELOG"));

	        rs.close();
	        return parameter;
	    }

	    public ArrayList<SettingsPOJO> getParameters() throws Exception{
	        String query;
	        ArrayList<SettingsPOJO> parameters = null;
	        query = "SELECT * FROM GASETTINGS";

	        ResultSet rs = db.execQuery (query);
	        
	        if(rs != null){
		        parameters = new ArrayList<SettingsPOJO>();
	        	SettingsPOJO parameter = null;
	        	
	        	while(rs.next()){
	        		parameter = new SettingsPOJO();
	        		
	        		parameter.setParameterID(rs.getInt("ID"));
	        		parameter.setIndividualSize(rs.getInt("INDIVIDUALSIZE"));
	        		parameter.setPopulationSize(rs.getInt("POPULATIONSIZE"));
	        		parameter.setNumOfCrossoverPoints(rs.getInt("CROSSOVERPOINTS"));
	        		parameter.setMutationRate(rs.getInt("MUTATIONRATE"));
	        		parameter.setMaxPreservedIndividuals(rs.getInt("PRESERVEDINDIVIDUALS"));
	        		parameter.setNumOfFitIndividualsToStop(rs.getInt("FITINDIVIDUALSTOSTOP"));
	        		parameter.setMaxGenerationsToStop(rs.getInt("GENERATIONSTOSTOP"));
	        		parameter.setScheduleKeyGeneration(rs.getBoolean("SCHEDULEKEYGENERATION"));
	        		parameter.setWriteLog(rs.getBoolean("WRITELOG"));
	        		
	        		parameters.add(parameter);
	        	 }
	        	rs.close();
	        }
	        return parameters;
	    }	    
}
