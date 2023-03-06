package com.fate.userservice.service;

import com.fate.userservice.entity.Hotel;
import com.fate.userservice.entity.Rating;
import com.fate.userservice.entity.User;
import com.fate.userservice.exception.ResourceNotFoundException;
import com.fate.userservice.feignclient.HotelFeign;
import com.fate.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelFeign hotelFeign;

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        Long randomId = (long) (Math.random() * 999999 );
        user.setId(randomId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long userId) {
         User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User with give id is not found on server : "+ userId)
                );

        Rating[] ratingOfUser =
                restTemplate.getForObject("http://localhost:8083/api/findByUserId/"+ user.getId(),Rating[].class);
        logger.info("{}",ratingOfUser);

        List<Rating> ratings = Arrays.stream(ratingOfUser).collect(Collectors.toList());

        List<Rating> ratingList = ratings.stream().map(rating -> {
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://localhost:8082/api/findById/"+ rating.getHotelId(), Hotel.class);
//            Hotel hotel = forEntity.getBody();
            Hotel hotel = hotelFeign.findOne(rating.getHotelId());
//            logger.info("{}",forEntity.getStatusCode());

            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        user.setRatingList(ratingList);
        return user;
    }
}
