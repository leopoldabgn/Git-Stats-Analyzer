package up.visulog.analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;

public class Analyzer {
    private final Configuration config;
    private String dayA;
    private String dayB;
    

    private AnalyzerResult result;

    public Analyzer(Configuration config) {
        this.config = config;
    }

    public Analyzer(Configuration config, String dayA, String dayB) {
        this.config = config;
        this.dayA = dayA;
        this.dayB = dayB;
    }

    public AnalyzerResult computeResults() {
        List<AnalyzerPlugin> plugins = new ArrayList<>();
        for (var pluginConfigEntry: config.getPluginConfigs().entrySet()) {
            var pluginName = pluginConfigEntry.getKey();
            var pluginConfig = pluginConfigEntry.getValue();
            var plugin = makePlugin(pluginName, pluginConfig);
            plugin.ifPresent(plugins::add);
        }
        // run all the plugins
        // TODO: try running them in parallel
        for (var plugin: plugins) plugin.run();

        // store the results together in an AnalyzerResult instance and return it
        return new AnalyzerResult(plugins.stream().map(AnalyzerPlugin::getResult).collect(Collectors.toList()));
    }

    // TODO: find a way so that the list of plugins is not hardcoded in this factory
    private Optional<AnalyzerPlugin> makePlugin(String pluginName, PluginConfig pluginConfig) {
        switch (pluginName) {
            case "countCommits" : return Optional.of(new CountCommitsPerAuthorPlugin(config));
            case "countCommitsPerWeekday": return Optional.of(new CountCommitsPerWeekdayPlugin(config));
            case "countMergeCommits": return Optional.of(new CountMergeCommitsPlugin(config));
            case "countLinesChanged": return Optional.of(new CountLinesChangedPlugin(config));
            case "countOnOneDay" : if(dayA.equals(dayB))
            return Optional.of(new CountCommitOnOneDay(config, dayA));
            case "countBetweenDays" : return Optional.of(new CountCommitsBetweenDays(config, dayA, dayB));
            default : return Optional.empty();
        }

    }

}
