package com.meysam.users.service.impl;

import com.meysam.common.dao.MemberRepository;
import com.meysam.common.model.dto.*;
import com.meysam.common.model.entity.Member;
import com.meysam.common.model.entity.MemberWallet;
import com.meysam.common.service.api.AuthServiceClient;
import com.meysam.users.service.api.MemberService;
import com.meysam.common.utils.exception.BusinessException;
import com.meysam.common.utils.messages.LocaleMessageSourceService;
import com.meysam.users.service.api.WalletServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final LocaleMessageSourceService messageSourceService;
    private final WalletServiceClient walletServiceClient;
    private final AuthServiceClient authServiceClient;


    @Override
    public Member createMember(Member user) {
        return memberRepository.save(user);
    }

    @Override
    public ResponseEntity login(LoginRequestDto loginRequestDto) {
        return authServiceClient.userLogin(loginRequestDto);
    }

    @Override
    public ResponseEntity register(RegisterUserRequestDto registerUserRequestDto) {
        return authServiceClient.registerUser(registerUserRequestDto);
    }

    @Override
    public Member findByUserName(String username) {
        return memberRepository.findByUsername(username);
    }

    @Override
    public Member findById(BigDecimal id) {
        return memberRepository.findById(id).orElse(null);
    }

    @Override
    public List<Member> findAll() {
        return (List<Member>) memberRepository.findAll();
    }

    @Override
    public UserWalletsDto getUserWallets(String username) {

        Member user = memberRepository.findByUsername(username);
        if(user!=null){

            List<MemberWallet> wallets = (List<MemberWallet>) walletServiceClient.getWallets(user.getUsername()).getBody();

            List<MemberWalletDto> walletDtos = wallets.stream().map(wallet -> MemberWalletDto.builder()
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
