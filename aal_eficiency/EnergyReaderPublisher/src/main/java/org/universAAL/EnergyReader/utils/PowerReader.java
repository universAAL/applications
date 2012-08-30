package org.universAAL.EnergyReader.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.universAAL.EnergyReader.model.*;

public class PowerReader {
void EnergyReader(){}
	
	public ReadEnergyModel[] readEnergyConsumption(){
		ReadEnergyModel[] consumption;
		
		HttpURLConnection connection;
        OutputStreamWriter request = null;

            URL url = null;   
            String response = null;        
            String[] data;
            try
            {
                url = new URL(Constants.gatewayURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestMethod("POST");    

                request = new OutputStreamWriter(connection.getOutputStream());
                request.flush();
                request.close();                           
                InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                	                
                for (String line = null; (line = reader.readLine()) != null;) {
                    sb.append(line).append("\n");
                }
                
                // Response from server after login process will be stored in response variable.                
                response = sb.toString();
                data = response.split("\n");
                isr.close();
                reader.close();
            }
            catch(IOException e){
                return null;
            } 
            consumption = new ReadEnergyModel[data.length];
            for (int i=0;i<data.length;i++){
            	String[] meassure = data[i].split("\t");
            	DeviceModel d = new DeviceModel();
            	d.setName(meassure[1]);
            	MeasurementModel m = new MeasurementModel();
            	m.setMeasurement(Integer.parseInt(meassure[4]));
            	m.setUnit("w");
            	ReadEnergyModel energy = new ReadEnergyModel();
            	energy.setDevice(d);
            	energy.setMeasure(m);
            	consumption[i]=energy;
            }
		return consumption;
	}

	
}
