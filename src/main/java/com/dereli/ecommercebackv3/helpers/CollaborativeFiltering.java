package com.dereli.ecommercebackv3.helpers;

import com.dereli.ecommercebackv3.daos.C2PDao;
import com.dereli.ecommercebackv3.daos.CustomerDao;
import com.dereli.ecommercebackv3.daos.ProductDao;
import com.dereli.ecommercebackv3.models.Customer;
import com.dereli.ecommercebackv3.models.Customer2ProductRating;
import com.dereli.ecommercebackv3.models.Product;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CollaborativeFiltering {

    private Map<Long, Map<Long, Double>> userItemMatrix;

    @Resource
    private ProductDao productDao;

    @Resource
    private C2PDao c2PDao;

    @Resource
    private CustomerDao customerDao;

    public CollaborativeFiltering() {

        userItemMatrix = new HashMap<>();
    }

    @PostConstruct
    public void executeCollaborativeFiltering(){
        System.out.println("collaborative filtering running");
        List<Customer2ProductRating>  ratingList = c2PDao.findAllC2PRatings();

        for(Customer2ProductRating c2p : ratingList){
            addUserItemInteraction(c2p.getCustomer().getPk(), c2p.getProduct().getPk(), c2p.getRating());
        }

        List<Long> customerIds = ratingList.stream().map(c2p -> c2p.getCustomer().getPk())
                .distinct()
                .collect(Collectors.toList());

        List<Long> productIds = productDao.getAllProductId();

        for(Long customer: customerIds){
            for(Long product: productIds){
                Double rating = userItemMatrix.get(customer).getOrDefault(product, (double) 0);
                if(rating == 0){
                   double prediction = predictPreference(customer, product);
                   if(prediction > 0 ) {
                       Customer customerObj = customerDao.getCustomerByPk(customer);
                       Product productObj = productDao.getProductByPk(product);
                       Customer2ProductRating newC2P = new Customer2ProductRating();
                       newC2P.setCustomer(customerObj);
                       newC2P.setProduct(productObj);
                       newC2P.setRating(prediction);
                       c2PDao.save(newC2P);
                   }
                }
            }

        }
    }

    public void addUserItemInteraction(Long userId, Long itemId, double rating) {
        userItemMatrix.computeIfAbsent(userId, k -> new HashMap<>()).put(itemId, rating);
    }

    private double cosineSimilarity(Map<Long, Double> v1, Map<Long, Double> v2) {
        double dotProduct = 0.0;
        double normV1 = 0.0;
        double normV2 = 0.0;

        for (Map.Entry<Long, Double> entry : v1.entrySet()) {
            Long itemId = entry.getKey();
            double rating1 = entry.getValue();
            Double rating2 = v2.get(itemId);
            if (rating2 != null) {
                dotProduct += rating1 * rating2;
                normV1 += Math.pow(rating1, 2);
                normV2 += Math.pow(rating2, 2);
            }
        }

        if (normV1 == 0.0 || normV2 == 0.0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(normV1) * Math.sqrt(normV2));
    }

    public double predictPreference(Long userId, Long itemId) {
        Map<Long, Double> userRatings = userItemMatrix.get(userId);
        if (userRatings == null) {
            return 0.0;
        }


        Map<Long, Double> similarUsers = new HashMap<>();
        for (Map.Entry<Long, Map<Long, Double>> entry : userItemMatrix.entrySet()) {
            Long otherUserId = entry.getKey();
            if (!otherUserId.equals(userId)) {
                double similarity = cosineSimilarity(userRatings, entry.getValue());
                if (similarity > 0.0) {
                    similarUsers.put(otherUserId, similarity);
                }
            }
        }

        if (similarUsers.isEmpty()) {
            return 0.0;
        }


        double weightedSum = 0.0;
        double similaritySum = 0.0;
        for (Map.Entry<Long, Double> entry : similarUsers.entrySet()) {
            Long otherUserId = entry.getKey();
            double similarity = entry.getValue();
            Map<Long, Double> otherUserRatings = userItemMatrix.get(otherUserId);
            Double rating = otherUserRatings.get(itemId);
            if (rating != null) {
                weightedSum += rating * similarity;
                similaritySum += similarity;
            }
        }

        return similaritySum > 0.0 ? weightedSum / similaritySum : 0.0;
    }
}
