package up.visulog.config;

import java.util.HashMap;

// TODO: define what this type should be (probably a Map: settingKey -> settingValue)
// TODO: voir si c'est mieux de laisser une interface 
public abstract class PluginConfig {
    protected HashMap<String, String> pluginConfig;

    public PluginConfig() {
  	this.pluginConfig = new HashMap<String, String>();
        pluginConfig.put("graph", "");
        pluginConfig.put("date", "");
        pluginConfig.put("startDate", "");
        pluginConfig.put("endDate", "");
	
	}

    public HashMap<String, String> getPluginConfig() {
        return pluginConfig;
    }

    public boolean isGraph() {
        return !pluginConfig.get("graph").equals("");
    }

    public String getGraph() {
        return pluginConfig.get("graph");
    }

    public String getDate() {
        return pluginConfig.get("date"); 
    }

    public String getStartDate() {
        return pluginConfig.get("startDate"); 
    }

    public String getEndDate() {
        return pluginConfig.get("endDate"); 
    }

}
