use Client_Scheduler;
DROP TRIGGER IF EXISTS before_appointment_insert;
DROP TRIGGER IF EXISTS before_appointment_update;

DELIMITER $$
CREATE TRIGGER before_appointment_insert
BEFORE INSERT ON Appointments
FOR EACH ROW
BEGIN
  IF EXISTS (SELECT * FROM Appointments
      WHERE Start <= new.End
      AND End >= new.Start) THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Overlaps with an existing appointment';
  END IF;
END $$

CREATE TRIGGER before_appointment_update
BEFORE UPDATE ON Appointments
FOR EACH ROW
BEGIN
  IF EXISTS (SELECT * FROM Appointments
      WHERE Start <= new.End
      AND End >= new.Start
      AND Appointment_ID != NEW.Appointment_ID) THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Overlaps with an existing appointment';
  END IF;
END $$
DELIMITER ;

