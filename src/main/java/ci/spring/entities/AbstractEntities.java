package ci.spring.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 *
 */
@Getter
@Setter
@ToString
public abstract class AbstractEntities {

    @Column(name = "datecreation", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateCreation = new Date();

    @Column(name = "datemodification")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateModification = new Date();


}
