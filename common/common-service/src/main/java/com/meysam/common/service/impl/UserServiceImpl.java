package com.meysam.common.service.impl;

import com.meysam.common.dao.UserRepository;
import com.meysam.common.model.entity.User;
import com.meysam.common.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    Logger logger= LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;


    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }


//    private final Environment environment;
//    // for this injection, the object must exist
//    // in app context and for this reason we created
//    // a bean for it in application class:
//    private final BCryptPasswordEncoder encoder;
//    private final RestTemplate restTemplate;

//    //fein client:
//    private final AlbumsServiceClient albumsServiceClient;
//
//    public UserServiceImpl(Environment environment, UserRepository userRepository,
//                           BCryptPasswordEncoder encoder, RestTemplate restTemplate, AlbumsServiceClient albumsServiceClient) {
//        this.environment = environment;
//        this.userRepository = userRepository;
//        this.encoder = encoder;
//        this.restTemplate = restTemplate;
//        this.albumsServiceClient = albumsServiceClient;
//    }

//    @Override
//    public User createUser(User user) {
//        //set public userID (randomly)
//        user.setUserId(UUID.randomUUID().toString());
//
//        //map incoming model to a User object
//        ModelMapper mapper=new ModelMapper();
//        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        User userEntity= mapper.map(user,User.class);
//
//        userEntity.setEncryptedPassword(encoder.encode(userEntity.getPassword()));
//
//        userRepository.save(userEntity);
//        return userEntity;
//    }

//    @Override
//    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User userEntity=  userRepository.findByEmail(username);
//
//        if(userEntity==null)throw new UsernameNotFoundException(username);
//
//        return new CustomUserDetails(userEntity.getUserId(),userEntity.getEmail(),
//                userEntity.getEncryptedPassword(),
//                true, true,
//                true, true,
//               new ArrayList<>());
//
//    }

////    @Override
////    public UserResponseModel getUserByUserID(String id) {
////
////        User user=userRepository.findByUserId(id);
////        if(user==null) throw new UsernameNotFoundException("User Not Found");
////
////        UserResponseModel returnValue = new ModelMapper().map(user, UserResponseModel.class);
////
////        /* restTemplate will ask Eureka about all addresses about albums-ws: */
//////        String albumsURL= String.format(environment.getProperty("albums.url"),id);
////
////        /* Using restTemplate (has load balancing functionality), we are
////        getting albums from other MS and converting it to a List of AlbumEntity : */
//////        ResponseEntity<List<AlbumEntity>> albumsListResponse =restTemplate.exchange(albumsURL, HttpMethod.GET, null,
//////                new ParameterizedTypeReference<List<AlbumEntity>>() {
//////                });
//////        List<AlbumEntity> albumsList=albumsListResponse.getBody();
////
////        /* Using Feign client insted of restTemplate: */
////        List<AlbumEntity> albumsList= albumsServiceClient.getAlbums(id);
////
//////        try {
//////            albumsList=albumsServiceClient.getAlbums(id);
//////        }catch (FeignException e){
//////            logger.error(e.getLocalizedMessage());
//////        }
//
//
//        returnValue.setAlbums(albumsList);
//        return returnValue;
//    }
}
