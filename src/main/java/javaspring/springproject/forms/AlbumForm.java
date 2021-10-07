package javaspring.springproject.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumForm {
    private int id;
    private String title;
    private String author;
}
