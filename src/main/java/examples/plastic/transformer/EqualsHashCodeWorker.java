package examples.plastic.transformer;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.plastic.FieldHandle;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodDescription;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticClassTransformer;
import org.apache.tapestry5.plastic.PlasticField;

import examples.plastic.annotations.ImplementsEqualsHashCode;

public class EqualsHashCodeWorker implements PlasticClassTransformer {

  private MethodDescription EQUALS = new MethodDescription("boolean", "equals",
      "java.lang.Object");

  private MethodDescription HASHCODE = new MethodDescription("int", "hashCode");

  private static final int PRIME = 37;

  /** Null safe comparison of two objects. */
  private static boolean compare(Object left, Object right) {
    return left == right || left.equals(right);
  }

  public void transform(PlasticClass plasticClass) {

    if (!plasticClass.hasAnnotation(ImplementsEqualsHashCode.class)) {
      return;
    }

    List<PlasticField> fields = plasticClass.getAllFields();

    final List<FieldHandle> handles = new ArrayList<FieldHandle>();

    for (PlasticField field : fields) {
      handles.add(field.getHandle());
    }

    plasticClass.introduceMethod(HASHCODE).addAdvice(new MethodAdvice() {

      public void advise(MethodInvocation invocation) {

        Object instance = invocation.getInstance();

        int result = 1;

        for (FieldHandle handle : handles) {

          Object fieldValue = handle.get(instance);

          if (fieldValue != null)
            result = (result * PRIME) + fieldValue.hashCode();
        }

        invocation.setReturnValue(result);

        // Don't proceed to the empty introduced method.
      }
    });

    plasticClass.introduceMethod(EQUALS).addAdvice(new MethodAdvice() {

      public void advise(MethodInvocation invocation) {

        // Until proven otherwise.

        Object thisInstance = invocation.getInstance();
        Object otherInstance = invocation.getParameter(0);

        invocation.setReturnValue(isEqual(thisInstance, otherInstance));

        // Don't proceed to the empty introduced method.
      }

      private boolean isEqual(Object thisInstance, Object otherInstance) {
        if (thisInstance == otherInstance) {
          return true;
        }

        if (otherInstance == null) {
          return false;
        }

        if (!(thisInstance.getClass() == otherInstance.getClass())) {
          return false;
        }

        for (FieldHandle handle : handles) {
          Object thisValue = handle.get(thisInstance);
          Object otherValue = handle.get(otherInstance);

          if (!(thisValue == otherValue || thisValue.equals(otherValue))) {
            return false;
          }
        }

        return true;
      }

    });

  }
}
