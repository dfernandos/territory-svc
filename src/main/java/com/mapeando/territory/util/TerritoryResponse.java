package com.mapeando.territory.util;

import com.mapeando.territory.entity.Territory;

import java.util.List;

public class TerritoryResponse {
    private List<Territory> territories;

    public TerritoryResponse(List<Territory> territories) {
        this.territories = territories;
    }

    public List<Territory> getTerritories() {
        return territories;
    }

    public void setTerritories(List<Territory> territories) {
        this.territories = territories;
    }
}

