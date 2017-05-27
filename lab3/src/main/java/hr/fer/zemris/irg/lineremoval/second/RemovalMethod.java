package hr.fer.zemris.irg.lineremoval.second;

import hr.fer.zemris.irg.lab1.linalg.icg.ICG;
import hr.fer.zemris.irg.lab1.linalg.vectors.IVector;
import hr.fer.zemris.irg.shapes3D.models.ObjectModel;

/**
 * Created by Dominik on 8.5.2017..
 */
public enum RemovalMethod {
    WITHOUT_REMOVAL {
        @Override
        public void execute(ObjectModel model, IVector eye) {
            model.drawAll();
        }
    }, FIRST_METHOD {
        @Override
        public void execute(ObjectModel model, IVector eye) {
            model.determineFaceVisibilities1(eye);
        }
    }, SECOND_METHOD {
        @Override
        public void execute(ObjectModel model, IVector eye) {
            model.determineFaceVisibilities2(eye);
        }
    }, THIRD_METHOD {
        @Override
        public void execute(ObjectModel model, IVector eye) {
            model.drawAll();
        }

        @Override
        public boolean projection(IVector first, IVector second, IVector third) {
            return ICG.isAntiClockWise(first.copy().set(2, 1), second.copy().set(2, 1), third.copy().set(2, 1));
        }
    };

    public boolean projection(IVector first, IVector second, IVector third) {
        return true;
    }

    public abstract void execute(ObjectModel model, IVector eye);
}
