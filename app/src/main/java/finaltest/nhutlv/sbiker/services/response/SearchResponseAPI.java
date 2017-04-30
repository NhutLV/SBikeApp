package finaltest.nhutlv.sbiker.services.response;

import java.util.List;

import finaltest.nhutlv.sbiker.entities.PlaceSearch;

/**
 * Created by NhutDu on 30/04/2017.
 */

public class SearchResponseAPI {

    private List<PlaceSearch> results;
    private String status;

    public SearchResponseAPI(List<PlaceSearch> results, String status) {
        this.results = results;
        this.status = status;
    }

    public List<PlaceSearch> getResults() {
        return results;
    }

    public void setResults(List<PlaceSearch> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
