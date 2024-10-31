package constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileType {
    CONTROLLER("/templates/controller/defaultControllerTemplate.txt", "Controller.java"),
    SERVICE("/templates/service/defaultServiceTemplate.txt", "Service.java"),
    REPOSITORY("/templates/repository/defaultRepositoryTemplate.txt", "Repository.java"),
    MODEL("/templates/model/defaultModelTemplate.txt", ".java"),
    DTO("/templates/dto/defaultDTOTemplate.txt", "DTO.java"),
    MAPPER("/templates/mapper/defaultMapperTemplate.txt", "Mapper.java"),
    REQUEST("/templates/request/defaultRequestTemplate.txt", "Request.java"),
    RESPONSE("/templates/response/defaultResponseTemplate.txt", "Response.java");

    private final String templatePath;
    private final String defaultFileName;
}
