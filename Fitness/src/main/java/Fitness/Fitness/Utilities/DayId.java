    package Fitness.Fitness.Utilities;

    import java.io.Serializable;
    import java.time.LocalDate;
    import java.util.Date;
    import java.util.Objects;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import jakarta.persistence.*;
    import jakarta.persistence.Temporal;
    import jakarta.persistence.TemporalType;
    import org.hibernate.annotations.CreationTimestamp;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
    public class DayId implements Serializable {

        @Temporal(TemporalType.DATE)
        @CreationTimestamp
        private LocalDate date;

        private Long personId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DayId dayId = (DayId) o;
            return Objects.equals(date, dayId.date) && Objects.equals(personId, dayId.personId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(date, personId);
        }
    }