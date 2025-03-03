package lt.restservice.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "image")
@Getter
@Setter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_blob", nullable = false)
    private final byte[] imageBlob;

    @Column(name = "name", nullable = false)
    private final String name;

    @Column(name = "description", nullable = false)
    private final String description;

    @Column(name = "date", nullable = false)
    private final Date date;

    // Owning side
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id", nullable = false)
    private final Author author;

    // Owning side
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "images_tags",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private final Set<Tag> tags;

    @Column(name = "timestamp", nullable = false)
    private final Timestamp uploadDate;

    @Column(name = "thumbnail", nullable = false)
    private final byte[] thumbnail;

}
