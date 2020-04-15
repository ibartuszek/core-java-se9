package tutorial.ch03.exercise11;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

public interface FilteredFileProvider {

    File[] getFiles(final List<File> store, final int index);

    class WithMethodReference implements FilteredFileProvider {
        @Override
        public File[] getFiles(final List<File> store, final int index) {
            return store.get(index).listFiles(File::isDirectory);
        }
    }

    class WithLambdaExpression implements FilteredFileProvider {
        @Override
        public File[] getFiles(final List<File> store, final int index) {
            return store.get(index).listFiles(file -> file.isDirectory());
        }
    }

    class WithAnonymousClass implements FilteredFileProvider {
        @Override
        public File[] getFiles(final List<File> store, final int index) {
            return store.get(index).listFiles(new FileFilter() {
                @Override
                public boolean accept(final File file) {
                    return file.isDirectory();
                }
            });
        }
    }

}


