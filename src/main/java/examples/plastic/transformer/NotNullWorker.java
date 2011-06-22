package examples.plastic.transformer;

import org.apache.tapestry5.plastic.ComputedValue;
import org.apache.tapestry5.plastic.FieldConduit;
import org.apache.tapestry5.plastic.InstanceContext;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticClassTransformer;
import org.apache.tapestry5.plastic.PlasticField;

import examples.plastic.annotations.NotNull;

public class NotNullWorker implements PlasticClassTransformer {

  public void transform(PlasticClass plasticClass) {

    for (PlasticField field : plasticClass
        .getFieldsWithAnnotation(NotNull.class)) {

      final String className = plasticClass.getClassName();
      final String fieldName = field.getName();

      field.setComputedConduit(new ComputedValue<FieldConduit<?>>() {

        public FieldConduit<Object> get(InstanceContext context) {

          return new FieldConduit<Object>() {
            private Object fieldValue;

            public Object get(Object instance, InstanceContext context) {

              return fieldValue;
            }

            public void set(Object instance, InstanceContext context,
                Object newValue) {

              if (newValue == null)
                throw new IllegalArgumentException(String.format(
                    "Field %s of class %s may not be assigned null.",
                    className, fieldName));

              fieldValue = newValue;
            }
          };
        }
      });
    }
  }
}
