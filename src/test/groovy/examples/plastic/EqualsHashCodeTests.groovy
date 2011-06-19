package examples.plastic

import org.apache.tapestry5.internal.plastic.StandardDelegate
import org.apache.tapestry5.plastic.ClassInstantiator
import org.apache.tapestry5.plastic.PlasticManager
import org.apache.tapestry5.plastic.PlasticManagerDelegate

import spock.lang.Specification
import examples.plastic.transformed.EqualsDemo
import examples.plastic.transformer.EqualsHashCodeWorker

class EqualsHashCodeTests extends Specification {

	PlasticManagerDelegate delegate = new StandardDelegate(new EqualsHashCodeWorker())

	PlasticManager mgr = PlasticManager.withContextClassLoader().packages([
		"examples.plastic.transformed"
	]).delegate(delegate).create();

	ClassInstantiator instantiator = mgr.getClassInstantiator(EqualsDemo.class.name)

	def instance = instantiator.newInstance()

	def "simple comparison"() {
		def instance1 = instantiator.newInstance()
		def instance2 = instantiator.newInstance()
		def instance3 = instantiator.newInstance()
		def instance4 = instantiator.newInstance()

		instance1.intValue = 99
		instance1.stringValue = "Hello"

		instance2.intValue = 100
		instance2.stringValue = "Hello"

		instance3.intValue = 99
		instance3.stringValue = "Goodbye"

		instance4.intValue = 99
		instance4.stringValue = "Hello"

		expect:

		instance1 != instance2

		instance1 != instance3

		instance1 == instance4
	}

	def "comparison against other object type is false"() {
		expect:

		instance != "some string"
	}

	def "comparison against null is false"() {
		expect:

		instance != null
	}
}
