package lt.restservice.model;

import java.util.Objects;

import org.hibernate.proxy.HibernateProxy;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "author")
@Getter
@Setter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_name", nullable = false)
    private final String name;

    // https://jpa-buddy.com/blog/hopefully-the-final-article-about-equals-and-hashcode-for-jpa-entities-with-db-generated-ids/
    @Override
    public final boolean equals(Object o) {
        // Check if 'this' the same as 'o'
        if (this == o) {
            return true;
        }
        // Check if 'o' is null
        if (o == null) {
            return false;
        }
        // Check if the class of 'o' is the same as the class of 'this',
        // If 'o' or 'this' is a Hibernate proxy, retrieve its actual persistent class (the one it proxies).
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) {
            return false;
        }
        // Cast 'o' to Author object
        // Ensure that 'this' has an id and compare it to the id of the other object 'author'
        Author author = (Author) o;
        return getId() != null && Objects.equals(getId(), author.getId());
    }

    @Override
    public final int hashCode() {
        // Check if its Hibernate proxy, return hashCode of persistent class the proxy represents,
        // Otherwise simply return hashCode of the class of the object.
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}
