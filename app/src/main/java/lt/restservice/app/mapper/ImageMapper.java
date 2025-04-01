package lt.restservice.app.mapper;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import lt.restservice.app.dto.ImageSaveRequest;
import lt.restservice.app.dto.ImageSearchRequest;
import lt.restservice.app.dto.ImageUpdateResponse;
import lt.restservice.app.dto.ImageViewResponse;
import lt.restservice.app.dto.ThumbnailListDto;
import lt.restservice.bl.model.CreateImageModel;
import lt.restservice.bl.model.ImageSearchModel;
import lt.restservice.bl.model.ImageUpdateModel;
import lt.restservice.bl.model.ImageViewModel;
import lt.restservice.bl.model.ThumbnailListModel;
import lt.restservice.bl.service.ImageService;

@Component
@RequiredArgsConstructor
@Transactional
public class ImageMapper {

    private final ImageService imageService;

    public void upload(ImageSaveRequest imageSaveRequest, MultipartFile multipartFile) throws IOException {
        CreateImageModel createImageModel = imageSaveRequest.toCreateImageModel(multipartFile);
        imageService.upload(createImageModel);
    }

    public Page<ThumbnailListDto> search(ImageSearchRequest imageSearchRequest) {
        ImageSearchModel imageSearchModel = imageSearchRequest.toImageSearch();
        Page<ThumbnailListModel> thumbnailListDtoPage = imageService.search(imageSearchModel);

        return of(thumbnailListDtoPage);
    }

    private static Page<ThumbnailListDto> of(Page<ThumbnailListModel> thumbnailListDtoPage) {
        return thumbnailListDtoPage.map(ThumbnailListDto::of);
    }

    public ImageViewResponse view(Long id) {
        ImageViewModel imageViewModel = imageService.view(id);

        return ImageViewResponse.of(imageViewModel);
    }

    public ImageUpdateResponse update(ImageSaveRequest imageSaveRequest, MultipartFile imageFile) throws IOException {
        ImageUpdateModel imageUpdateModel = imageSaveRequest.toImageUpdateModel(imageFile);

        return ImageUpdateResponse.of(imageService.update(imageUpdateModel));
    }

    public void delete(Long id) {
        imageService.delete(id);
    }
}
