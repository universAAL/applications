package org.universAAL.AALapplication.medication_manager.persistence.impl;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.PrescriptionDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.PrescriptionFactory;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Log.*;

/**
 * @author George Fournadjiev
 */
public final class PrescriptionFactoryImpl implements PrescriptionFactory {

  private final PersistentService persistentService;

  public PrescriptionFactoryImpl(PersistentService persistentService) {

    this.persistentService = persistentService;
  }

  public void save(PrescriptionDTO prescriptionDTO) {
    info("PrescriptionFactoryImpl is not implemented yet", getClass());

    //TODO
  }
}
