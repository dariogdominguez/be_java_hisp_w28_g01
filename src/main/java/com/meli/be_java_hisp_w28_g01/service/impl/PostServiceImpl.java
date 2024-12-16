package com.meli.be_java_hisp_w28_g01.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.be_java_hisp_w28_g01.dto.request.PostDto;
import com.meli.be_java_hisp_w28_g01.dto.request.PromoPostDto;
import com.meli.be_java_hisp_w28_g01.dto.response.PostDtoResponse;
import com.meli.be_java_hisp_w28_g01.exception.AlreadyExistsException;
import com.meli.be_java_hisp_w28_g01.dto.request.ProductoDto;
import com.meli.be_java_hisp_w28_g01.dto.response.PostByUserDto;
import com.meli.be_java_hisp_w28_g01.dto.response.ResponsePostDto;
import com.meli.be_java_hisp_w28_g01.dto.response.SellerDto;
import com.meli.be_java_hisp_w28_g01.exception.NotFoundException;
import com.meli.be_java_hisp_w28_g01.model.Post;
import com.meli.be_java_hisp_w28_g01.model.Product;
import com.meli.be_java_hisp_w28_g01.model.PromoPost;
import com.meli.be_java_hisp_w28_g01.model.Seller;
import com.meli.be_java_hisp_w28_g01.repository.IPostRepository;
import com.meli.be_java_hisp_w28_g01.service.IFollowService;
import com.meli.be_java_hisp_w28_g01.service.IPostService;
import com.meli.be_java_hisp_w28_g01.service.IProductService;
import com.meli.be_java_hisp_w28_g01.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class PostServiceImpl implements IPostService {
    @Autowired
    ObjectMapper mapper;
    @Autowired
    IPostRepository repository;
    @Autowired
    IProductService productService;
    @Autowired
    ISellerService sellerService;
    @Autowired
    IFollowService followService;


    @Override
    public List<PostDtoResponse> getAll() {
        return repository.getAll()
                .stream()
                .map(s -> {
                    PostDtoResponse dto = mapper.convertValue(s, PostDtoResponse.class);
                    dto.setUserId(s.getSeller().getId());
                    return dto;
                })
                .toList();
    }

    @Override
    public PostDtoResponse getByid(int id) {
        if(id<0){
            throw new IllegalArgumentException("Id no válido");
        }

        return repository.getAll().stream().filter(p -> p.getId()==id)
                .map(m->mapper.convertValue(m,PostDtoResponse.class))
                .findFirst().orElseThrow(() -> new NotFoundException(id, "post"));
    }

    @Override
    public String add(PostDto postDto) {
        Product p = productService.findById(postDto.getProductoDto().getId()).orElse(null);
        if (p == null){
            p = productService.add(postDto.getProductoDto());
        }
        Seller seller = sellerService.findById(postDto.getUserId()).orElse(null);
        if (seller == null){
            throw new NotFoundException(postDto.getUserId(), "Seller");
        }
        LocalDate formatedDate = LocalDate.of(postDto.getDate().getYear(),postDto.getDate().getMonth(), postDto.getDate().getDayOfMonth());
        Post post = new Post(
                repository.getAll().size()+1,
                seller,
                formatedDate,
                p,
                postDto.getCategory(),
                postDto.getPrice()
        );
        repository.add(post);
        return "Se creó la publicación correctamente con el id: " + post.getId();
    }

    @Override
    public PostByUserDto getPostsByUser(int userId) {
        List<Integer> sellerIds = followService.getFollowedSeller(userId).getFollowed().stream()
                .map(SellerDto::getId)
                .toList();

        List<Post> filteredPosts = filterSellerPost(sellerIds);

        return convertToDto(filteredPosts, userId);
    }

    private List<Post> filterSellerPost(List<Integer> sellerIds) {
        LocalDate twoWeeksAgo = LocalDate.now().minusWeeks(2);

        return repository.getAll().stream()
                .filter( p -> sellerIds.contains(p.getSeller().getId()))
                .filter(post -> post.getDate() != null && post.getDate().isAfter(twoWeeksAgo))
                .sorted(Comparator.comparing(Post::getDate).reversed())
                .toList();
    }

    private PostByUserDto convertToDto(List<Post> filteredPosts, int userId) {
        List<ResponsePostDto> convertedPosts = filteredPosts.stream()
                .map(this::toResponsePostDto)
                .toList();

        return new PostByUserDto(userId, convertedPosts);
    }

    private ResponsePostDto toResponsePostDto(Post post) {
        ProductoDto productDto = new ProductoDto(
                post.getProduct().getId(),
                post.getProduct().getName(),
                post.getProduct().getType(),
                post.getProduct().getBrand(),
                post.getProduct().getColor(),
                post.getProduct().getNotes()
        );

        return new ResponsePostDto(
                post.getSeller().getId(),
                post.getId(),
                post.getDate(),
                productDto,
                post.getCategory(),
                post.getPrice()
        );
    }

    @Override
    public String addPromoPost(PromoPostDto promoPostDto) {

        Product p = productService.findById(promoPostDto.getProductoDto().getId()).orElse(null);
        if (p == null){
            p = productService.add(promoPostDto.getProductoDto());
        }

        Seller seller = sellerService.findById(promoPostDto.getUserId())
                .orElseThrow(() -> new NotFoundException(promoPostDto.getUserId(), "Seller"));

        LocalDate formatedDate = LocalDate.of(promoPostDto.getDate().getYear(),promoPostDto.getDate().getMonth(), promoPostDto.getDate().getDayOfMonth());
        PromoPost promoPost = new PromoPost(
                repository.getAll().size()+1,
                seller,
                formatedDate,
                p,
                promoPostDto.getCategory(),
                promoPostDto.getPrice()*promoPostDto.getDiscount(),
                promoPostDto.isHasPromo(),
                promoPostDto.getDiscount()
        );
        repository.add(promoPost);
        return "Se creó la publicación correctamente con el id: " + promoPost.getId();



    }
}
