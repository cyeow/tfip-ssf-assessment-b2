package tfip.ecommerce.ssfassessmentb2.util;

import java.security.SecureRandom;

public class Generate {
    
    public static String idString(Integer numChars) {
        StringBuilder sb = new StringBuilder();
        SecureRandom sr = new SecureRandom();

        while(sb.length() < numChars) {
            sb.append(Integer.toHexString(sr.nextInt(16)));
        }

        return sb.toString();
    }
}
