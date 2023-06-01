package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPrimaryStore;
  private TorpedoStore mockSecondaryStore;

  @BeforeEach
  public void init() {
    mockPrimaryStore = mock(TorpedoStore.class);
    mockSecondaryStore = mock(TorpedoStore.class);
    this.ship = new GT4500(mockPrimaryStore, mockSecondaryStore);
  }

  @Test
  public void fireTorpedo_Single_Success() {
    // Arrange
    when(mockPrimaryStore.isEmpty()).thenReturn(false);
    when(mockPrimaryStore.fire(1)).thenReturn(true);
    when(mockSecondaryStore.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryStore).isEmpty();
    verify(mockPrimaryStore).fire(1);
    verify(mockSecondaryStore, never()).isEmpty();
    verify(mockSecondaryStore, never()).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success() {
    // Arrange
    when(mockPrimaryStore.isEmpty()).thenReturn(false);
    when(mockPrimaryStore.fire(1)).thenReturn(true);
    when(mockSecondaryStore.isEmpty()).thenReturn(false);
    when(mockSecondaryStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryStore).isEmpty();
    verify(mockPrimaryStore).fire(1);
    verify(mockSecondaryStore).isEmpty();
    verify(mockSecondaryStore).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Success_T1Empty_T2Empty() {
    // Arrange
    when(mockPrimaryStore.isEmpty()).thenReturn(true);
    when(mockSecondaryStore.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(mockPrimaryStore).isEmpty();
    verify(mockPrimaryStore, never()).fire(1);
    verify(mockSecondaryStore).isEmpty();
    verify(mockSecondaryStore, never()).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Success_T1_T2Empty() {
    // Arrange
    when(mockPrimaryStore.isEmpty()).thenReturn(false);
    when(mockSecondaryStore.isEmpty()).thenReturn(true);
    when(mockPrimaryStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryStore).isEmpty();
    verify(mockPrimaryStore, times(1)).fire(1);
    verify(mockSecondaryStore, never()).isEmpty();
    verify(mockSecondaryStore, never()).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Success_T1Empty_T2() {
    // Arrange
    when(mockPrimaryStore.isEmpty()).thenReturn(true);
    when(mockSecondaryStore.isEmpty()).thenReturn(false);
    when(mockSecondaryStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryStore).isEmpty();
    verify(mockPrimaryStore, never()).fire(1);
    verify(mockSecondaryStore).isEmpty();
    verify(mockSecondaryStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Success_T1_T2() {
    // Arrange
    when(mockPrimaryStore.isEmpty()).thenReturn(false);
    when(mockSecondaryStore.isEmpty()).thenReturn(false);
    when(mockPrimaryStore.fire(1)).thenReturn(true);
    when(mockPrimaryStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryStore).isEmpty();
    verify(mockPrimaryStore, times(1)).fire(1);
    verify(mockSecondaryStore, never()).isEmpty();
    verify(mockSecondaryStore, never()).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Success_T1Empty_T2Empty_T1Last() {
    // Arrange
    when(mockPrimaryStore.isEmpty()).thenReturn(true);
    when(mockSecondaryStore.isEmpty()).thenReturn(true);

    // Act
    ship.setWasPrimaryFiredLast(true);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(mockPrimaryStore).isEmpty();
    verify(mockPrimaryStore, never()).fire(1);
    verify(mockSecondaryStore).isEmpty();
    verify(mockSecondaryStore, never()).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Success_T1_T2Empty_T1Last() {
    // Arrange
    when(mockPrimaryStore.isEmpty()).thenReturn(false);
    when(mockSecondaryStore.isEmpty()).thenReturn(true);
    when(mockPrimaryStore.fire(1)).thenReturn(true);

    // Act
    ship.setWasPrimaryFiredLast(true);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryStore).isEmpty();
    verify(mockPrimaryStore, times(1)).fire(1);
    verify(mockSecondaryStore).isEmpty();
    verify(mockSecondaryStore, never()).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Success_T1Empty_T2_T1Last() {
    // Arrange
    when(mockPrimaryStore.isEmpty()).thenReturn(true);
    when(mockSecondaryStore.isEmpty()).thenReturn(false);
    when(mockSecondaryStore.fire(1)).thenReturn(true);

    // Act
    ship.setWasPrimaryFiredLast(true);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryStore, never()).isEmpty();
    verify(mockPrimaryStore, never()).fire(1);
    verify(mockSecondaryStore).isEmpty();
    verify(mockSecondaryStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Success_T1_T2_T1Last() {
    // Arrange
    when(mockPrimaryStore.isEmpty()).thenReturn(false);
    when(mockSecondaryStore.isEmpty()).thenReturn(false);
    when(mockPrimaryStore.fire(1)).thenReturn(true);
    when(mockSecondaryStore.fire(1)).thenReturn(true);

    // Act
    ship.setWasPrimaryFiredLast(true);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryStore, never()).isEmpty();
    verify(mockPrimaryStore, never()).fire(1);
    verify(mockSecondaryStore).isEmpty();
    verify(mockSecondaryStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success_T1_T2() {
    // Arrange
    when(mockPrimaryStore.isEmpty()).thenReturn(false);
    when(mockSecondaryStore.isEmpty()).thenReturn(false);
    when(mockPrimaryStore.fire(1)).thenReturn(true);
    when(mockPrimaryStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryStore).isEmpty();
    verify(mockPrimaryStore, times(1)).fire(1);
    verify(mockSecondaryStore).isEmpty();
    verify(mockSecondaryStore, times(1)).fire(1);
  }

  /**
   * Sokkal konnyebb igy megirni a teszteket es sokkal jobban le lehet fedni a kodot.
   */
  @Test
  public void fireTorpedo_All_Success_T1Empty_T2Empty() {
    // Arrange
    when(mockPrimaryStore.isEmpty()).thenReturn(true);
    when(mockSecondaryStore.isEmpty()).thenReturn(true);
    when(mockPrimaryStore.fire(1)).thenReturn(true);
    when(mockSecondaryStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(mockPrimaryStore, never()).isEmpty();
    verify(mockPrimaryStore, never()).fire(1);
    verify(mockSecondaryStore).isEmpty();
    verify(mockSecondaryStore, never()).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success_T1_T2Empty() {
    // Arrange
    when(mockPrimaryStore.isEmpty()).thenReturn(false);
    when(mockSecondaryStore.isEmpty()).thenReturn(true);
    when(mockPrimaryStore.fire(1)).thenReturn(true);
    when(mockSecondaryStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(mockPrimaryStore, never()).isEmpty();
    verify(mockPrimaryStore, never()).fire(1);
    verify(mockSecondaryStore).isEmpty();
    verify(mockSecondaryStore, never()).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success_T1Empty_T2() {
    // Arrange
    when(mockPrimaryStore.isEmpty()).thenReturn(true);
    when(mockSecondaryStore.isEmpty()).thenReturn(false);
    when(mockPrimaryStore.fire(1)).thenReturn(true);
    when(mockSecondaryStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(mockPrimaryStore).isEmpty();
    verify(mockPrimaryStore, never()).fire(1);
    verify(mockSecondaryStore).isEmpty();
    verify(mockSecondaryStore, never()).fire(1);
  }
}
