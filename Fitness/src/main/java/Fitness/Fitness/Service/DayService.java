package Fitness.Fitness.Service;

import Fitness.Fitness.Entity.Day;
import Fitness.Fitness.Repository.DayRepo;
import Fitness.Fitness.Utilities.CurrentUser;
import Fitness.Fitness.Utilities.DayId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DayService {

    @Autowired
    private DayRepo dayRepository;

    public List<Day> getAllDays() {
        return dayRepository.findAll();
    }

    public Optional<Day> getDayByDateAndPersonId(LocalDate date, Long personId) {
        return dayRepository.findById(new DayId(date, personId));
    }

    public Day createDay(Day day) {
        return dayRepository.save(day);
    }

    public Day updateDay(LocalDate date, Day updatedDay) {
        if (dayRepository.existsByDateAndPersonId(date, CurrentUser.getInstance().getUserId())) {
            updatedDay.setDate(dayRepository.findByDateAndPersonId(date,CurrentUser.getInstance().getUserId()).getDate());
            updatedDay.setPersonId(CurrentUser.getInstance().getUserId());
            return dayRepository.save(updatedDay);
        }
        return null;
    }

    public void deleteDay(LocalDate date, Long personId) {
        dayRepository.deleteById(new DayId(date, personId));
    }

    public List<Day> getDaysForPerson(Long personId) {
        return dayRepository.findByPersonId(personId);
    }
}