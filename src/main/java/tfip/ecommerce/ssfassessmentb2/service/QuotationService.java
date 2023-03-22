package tfip.ecommerce.ssfassessmentb2.service;

import java.io.StringReader;
import java.util.List;

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
                .header("Accept", MediaType.APPLICATION_JSON_VALUE)
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(listToString(items));

        System.out.println(req);
        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.exchange(req, String.class);

        // if error or no response body
        if (!resp.getStatusCode().is2xxSuccessful() || resp.getBody().contains("error") ||
                resp.getBody() == null || resp.getBody().isEmpty()) {
            throw new Exception(resp.getBody());
        }

        return createFromJSON(resp.getBody());
    }

    private String listToString(List<String> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        items.forEach(item -> {
            sb.append("\"");
            sb.append(item);
            if(items.indexOf(item) != items.size()-1) {
                sb.append("\", ");
            } else {
                sb.append("\"");
            }
        });
        sb.append("]");
        
        return sb.toString();
    }

    private Quotation createFromJSON(String json) {

        JsonObject o = Json.createReader(new StringReader(json)).readObject();

        Quotation q = new Quotation();
        q.setQuoteId(o.getString("quoteId"));

        JsonObject quotationsMap = o.getJsonObject("quotations");
        for (String quote : quotationsMap.keySet()) {
            q.addQuotation(quote, Float.parseFloat(quotationsMap.get(quote).toString()));
        }

        return q;
    }
}
