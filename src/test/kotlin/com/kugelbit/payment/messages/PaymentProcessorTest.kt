package com.kugelbit.payment.messages

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class PaymentProcessorTest {

  @Test
  @DisplayName("should normalize name as lowercase to use in bindings")
  fun nameForBinding() {
    assertThat(PaymentProcessor.GERENCIA_NET.nameForBinding()).isEqualTo("gerencia_net")
    assertThat(PaymentProcessor.PAYPAL.nameForBinding()).isEqualTo("paypal")
  }

  @Test
  @DisplayName("to string should be equal to nameForBinding")
  fun toStringRepresentation() {
    assertThat(PaymentProcessor.PAYPAL.toString()).isEqualTo(PaymentProcessor.PAYPAL.nameForBinding())
  }
}