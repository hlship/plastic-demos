
package examples.plastic

import org.apache.tapestry5.internal.plastic.StandardDelegate
import org.apache.tapestry5.plastic.ClassInstantiator
import org.apache.tapestry5.plastic.PlasticClassTransformer
import org.apache.tapestry5.plastic.PlasticManager
import org.apache.tapestry5.plastic.PlasticManagerDelegate

import spock.lang.Specification

/**
 * Base class for tests, providing helpers for creating PlasticManager or ClassInstantiator.
 *
 */
class PlasticDemosSpecification extends Specification {

  PlasticManager createPlasticManager(PlasticClassTransformer transformer) {
    PlasticManagerDelegate delegate = new StandardDelegate(transformer)

    return PlasticManager.withContextClassLoader().packages([
      "examples.plastic.transformed"
    ]).delegate(delegate).create();
  }

  ClassInstantiator createInstantiator(PlasticClassTransformer transformer, Class transformClass) {
    return createPlasticManager(transformer).getClassInstantiator(transformClass.getName());
  }
}
