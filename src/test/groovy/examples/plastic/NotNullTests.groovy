package examples.plastic

import org.apache.tapestry5.internal.plastic.StandardDelegate
import org.apache.tapestry5.plastic.ClassInstantiator
import org.apache.tapestry5.plastic.PlasticManager
import org.apache.tapestry5.plastic.PlasticManagerDelegate

import spock.lang.Specification
import examples.plastic.transformed.NotNullDemo
import examples.plastic.transformer.NotNullWorker

class NotNullTests extends Specification {

  PlasticManagerDelegate delegate = new StandardDelegate(new NotNullWorker())

  PlasticManager mgr = PlasticManager.withContextClassLoader().packages([
    "examples.plastic.transformed"
  ]).delegate(delegate).create();

  ClassInstantiator instantiator = mgr.getClassInstantiator(NotNullDemo.class.name)

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

    e.message == "Field examples.plastic.transformed.NotNullDemo of class value may not be assigned null."
  }
}
