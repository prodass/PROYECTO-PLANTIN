package com.comunicaciones.plantas.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiPlantIdDTO {
    private Result result;

    // Getters y setters

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Result {
    private Classification classification;

    // Getters y setters

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Classification {
    private List<Suggestion> suggestions;

    // Getters y setters

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Suggestion {
    private String name;

    // Getters y setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
