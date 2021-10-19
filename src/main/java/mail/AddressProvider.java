package mail;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public final class AddressProvider {

    private static final String ADDRESSES_FILE_PATH = "addresses.json";

    private static class AddressesBean {
        private final Set<String> emails;

        // region Constructor, getters, equals, hashCode, toString -- generated by BoB the Builder of Beans
        // The code below has been generated by BoB the Builder of Beans based on the class' fields.
        // Everything after this comment will be regenerated if you invoke BoB again.
        // If you don't know who BoB is, you can find him here: https://bitbucket.org/atlassianlabs/bob-the-builder-of-beans

        public AddressesBean(Iterable<String> emails) {
            this.emails = ImmutableSet.copyOf(Objects.requireNonNull(emails));
        }

        // region Getters -- generated by BoB the Builder of Beans
        public Set<String> getEmails() {
            return emails;
        }// endregion Getters

        // region hashCode() and equals() -- generated by BoB the Builder of Beans
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            AddressesBean that = (AddressesBean) o;

            return Objects.equals(this.getEmails(), that.getEmails());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getEmails());
        }// endregion hashCode() and equals()

        // region toString() -- generated by BoB the Builder of Beans
        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("emails", getEmails())
                    .toString();
        }// endregion toString()
        // endregion Constructor, getters, equals, hashCode, toString
    }

    private AddressProvider() {
    }

    public static Set<String> getAllAddresses() {
        try {
            JsonReader reader = new JsonReader(new FileReader(ADDRESSES_FILE_PATH));
            Gson gson = new Gson();
            return ((AddressesBean) gson.fromJson(reader, AddressesBean.class)).getEmails();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }

    public static String getAllAddressesString() {
        return String.join(", ", getAllAddresses());
    }
}
