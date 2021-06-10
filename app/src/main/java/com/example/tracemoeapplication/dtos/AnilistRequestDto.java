package com.example.tracemoeapplication.dtos;

public class AnilistRequestDto {
    private final static String query = "query ($ids: [Int]) {\\n Page(page: 1, perPage: 50) {\\n media(id_in: $ids, type: ANIME) {\\n id\\n title {\\n native\\n romaji\\n english\\n }\\n type\\n format\\n status\\n startDate {\\n year\\n month\\n day\\n }\\n endDate {\\n year\\n … synonyms\\n studios {\\n edges {\\n isMain\\n node {\\n id\\n name\\n siteUrl\\n }\\n }\\n }\\n isAdult\\n externalLinks {\\n id\\n url\\n site\\n }\\n siteUrl\\n }\\n }\\n }\\n ";

    private VariablesDto variables;

    public AnilistRequestDto(){
        variables = new VariablesDto();
    }

    public static String getQuery() {
        return query;
    }

    public VariablesDto getVariables() {
        return variables;
    }

    public void setVariables(VariablesDto variables) {
        this.variables = variables;
    }
}
