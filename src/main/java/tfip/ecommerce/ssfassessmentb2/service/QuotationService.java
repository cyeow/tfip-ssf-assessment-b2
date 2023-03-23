package tfip.ecommerce.ssfassessmentb2.service;

import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import tfip.ecommerce.ssfassessmentb2.model.Quotation;

@Service
public class QuotationService {

    @Value("${qsys.url}")
    String qSysUrl;

    public Quotation getQuotations(List<String> items) throws Exception {
        String fullPath = UriComponentsBuilder.fromUriString(qSysUrl).path("/quotation").toUriString();

        RequestEntity<String> req = RequestEntity.post(fullPath)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(listToString(items));

        System.out.println(req);
        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.exchange(req, String.class);

        // if error or no response body
        if (!resp.getStatusCode().is2xxSuccessful() || resp.getBody().contains("error") ||
                resp.getBody() == null || resp.getBody().isEmpty()) {
            throw new Exception(resp.getBody());
        }

        return createFromJSON(resp.getBody(), items);
    }

    private String listToString(List<String> items) {        
        return items.stream().map(item -> "\"" + item + "\"").collect(Collectors.joining(", ", "[", "]"));
    }

    private Quotation createFromJSON(String json, List<String> items) {

        JsonObject o = Json.createReader(new StringReader(json)).readObject();

        Quotation q = new Quotation();
        q.setQuoteId(o.getString("quoteId"));
        
        JsonObject qtns = o.getJsonObject("quotations"); 
        System.out.println(qtns.toString());

        items.forEach(item -> q.addQuotation(item, (float) qtns.getJsonNumber(item).doubleValue()));

        return q;
    }
}
