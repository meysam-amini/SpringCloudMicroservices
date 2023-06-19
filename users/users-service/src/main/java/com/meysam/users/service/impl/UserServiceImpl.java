package com.meysam.users.service.impl;

import com.meysam.common.dao.UserRepository;
import com.meysam.common.model.dto.UserDto;
import com.meysam.common.model.dto.UserWalletDto;
import com.meysam.common.model.dto.UserWalletsDto;
import com.meysam.common.model.entity.User;
import com.meysam.common.model.entity.UserWallet;
import com.meysam.users.service.api.UserService;
import com.meysam.common.utils.exception.BusinessException;
import com.meysam.common.utils.messages.LocaleMessageSourceService;
import com.meysam.users.service.api.WalletServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LocaleMessageSourceService messageSourceService;
    private final WalletServiceClient walletServiceClient;


    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(BigDecimal id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserWalletsDto getUserWallets(String username) {

        User user = userRepository.findByUsername(username);
        if(user!=null){

            List<UserWallet> wallets = (List<UserWallet>) walletServiceClient.getWallets(user.getId()).getBody();

            List<UserWalletDto> walletDtos = wallets.stream().map(wallet -> UserWalletDto.builder()
                    .address(wallet.getAddress())
                    .coinUnit(wallet.getCoinUnit())
                    .build()).toList();

            UserDto userDto = UserDto.builder().id(user.getId()).username(username).build();

            return UserWalletsDto.builder().wallets(walletDtos).userDto(userDto).build();
        }
        else {
            throw new BusinessException(messageSourceService.getMessage("USER_NOT_FOUND"));
        }
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
