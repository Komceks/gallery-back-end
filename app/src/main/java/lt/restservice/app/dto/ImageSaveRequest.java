package lt.restservice.app.dto;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lt.restservice.app.marker.CreateRequest;
import lt.restservice.app.marker.UpdateRequest;
import lt.restservice.bl.model.CreateImageModel;
import lt.restservice.bl.model.ImageUpdateModel;

@Data
@AllArgsConstructor
public class ImageSaveRequest {
    @NotNull(groups = UpdateRequest.class)
    @Null(groups = CreateRequest.class)
    private final Long id;
    @NotBlank
    private final String imageName;
    private final String description;
    @NotBlank
    private final String authorName;
    @PastOrPresent
    private final LocalDate date;
    private final Set<String> tags;

    public CreateImageModel toCreateImageModel(MultipartFile multipartFile) throws IOException {
        return CreateImageModel.builder()
                .imageFile(multipartFile.getBytes())
                .imageName(imageName)
                .description(description)
                .date(date)
                .authorName(authorName)
                .tagNames(tags)
                .uploadDate(getLocalDateTimeNow())
                .build();
    }

    public ImageUpdateModel toImageUpdateModel(MultipartFile multipartFile) throws IOException {
        ImageUpdateModel.ImageUpdateModelBuilder builder = ImageUpdateModel.builder()
                .id(id)
                .imageName(imageName)
                .description(description)
                .authorName(authorName)
                .date(date)
                .tagNames(tags)
                .uploadDate(getLocalDateTimeNow());

        if (multipartFile != null) {
            builder.imageFile(multipartFile.getBytes());
        }

        return builder.build();
    }

    private LocalDateTime getLocalDateTimeNow() {
        return LocalDateTime.now();
    }

}