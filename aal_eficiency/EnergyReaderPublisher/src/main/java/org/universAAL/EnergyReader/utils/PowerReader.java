/*
	Copyright 2011-2012 TSB, http://www.tsbtecnologias.es
	TSB - Tecnologías para la Salud y el Bienestar
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
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
