package examples.plastic

import org.apache.tapestry5.plastic.ClassInstantiator
import org.apache.tapestry5.plastic.PlasticManager

import examples.plastic.transformed.NotNullDemo
import examples.plastic.transformer.NotNullWorker

class NotNullTests extends PlasticDemosSpecification {

  ClassInstantiator instantiator = createInstantiator(new NotNullWorker(), NotNullDemo.class)

  def "null default value is allowed"() {
    def o = instantiator.newInstance()

    expect:

    o.value == null
  }

  def "store and retrieve an actual value"() {
    def string = "this is not null"
    def o = instantiator.newInstance()

    when:

    o.value = string

    then:

    o.value.is(string)
  }

  def "store null is failure"() {

    def o = instantiator.newInstance()

    when:

    o.value = null

    then:

    def e = thrown(IllegalArgumentException)

    e.message == "Field value of class examples.plastic.transformed.NotNullDemo may not be assigned null."
  }
}
