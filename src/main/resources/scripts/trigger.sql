DO '
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_trigger
        WHERE tgname = ''set_timestamps_users''
    ) THEN
        CREATE TRIGGER set_timestamps_users
            BEFORE INSERT OR UPDATE ON users
            FOR EACH ROW
        EXECUTE FUNCTION set_timestamps();
    END IF;
END';

DO '
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_trigger
        WHERE tgname = ''set_timestamps_products''
    ) THEN
        CREATE TRIGGER set_timestamps_products
            BEFORE INSERT OR UPDATE ON products
            FOR EACH ROW
        EXECUTE FUNCTION set_timestamps();
    END IF;
END';

DO '
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_trigger
        WHERE tgname = ''set_timestamps_age_limit''
    ) THEN
        CREATE TRIGGER set_timestamps_age_limit
            BEFORE INSERT OR UPDATE ON age_limit
            FOR EACH ROW
        EXECUTE FUNCTION set_timestamps();
    END IF;
END';

DO '
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_trigger
        WHERE tgname = ''set_timestamps_genre''
    ) THEN
        CREATE TRIGGER set_timestamps_genre
            BEFORE INSERT OR UPDATE ON genre
            FOR EACH ROW
        EXECUTE FUNCTION set_timestamps();
    END IF;
END';

DO '
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_trigger
        WHERE tgname = ''set_timestamps_copy''
    ) THEN
        CREATE TRIGGER set_timestamps_copy
            BEFORE INSERT OR UPDATE ON copy
            FOR EACH ROW
        EXECUTE FUNCTION set_timestamps();
    END IF;
END';

DO '
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_trigger
        WHERE tgname = ''set_timestamps_location''
    ) THEN
        CREATE TRIGGER set_timestamps_location
            BEFORE INSERT OR UPDATE ON location
            FOR EACH ROW
        EXECUTE FUNCTION set_timestamps();
    END IF;
END';
