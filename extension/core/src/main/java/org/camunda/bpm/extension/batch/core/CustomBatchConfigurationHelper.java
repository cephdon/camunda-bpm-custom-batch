package org.camunda.bpm.extension.batch.core;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.persistence.entity.ByteArrayEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ByteArrayManager;

public class CustomBatchConfigurationHelper<T> {

  public static <T> CustomBatchConfigurationHelper<T> of() {
    return new CustomBatchConfigurationHelper<>();
  }

  public CustomBatchConfiguration<T> readConfiguration(byte[] serializedConfiguration) {
    List<T> data = SerializationUtils.deserialize(serializedConfiguration);
    return new CustomBatchConfiguration<>(data);
  }

  public ByteArrayEntity saveConfiguration(CustomBatchConfiguration jobConfiguration) {
    final ByteArrayManager byteArrayManager = Context.getCommandContext().getByteArrayManager();

    final ByteArrayEntity configurationEntity = new ByteArrayEntity();
    configurationEntity.setBytes(writeConfiguration(jobConfiguration));
    byteArrayManager.insert(configurationEntity);
    return configurationEntity;
  }

  public static byte[] writeConfiguration(CustomBatchConfiguration configuration) {
    return SerializationUtils.serialize((Serializable) configuration.getData());
  }

}
