CREATE OR REPLACE FUNCTION set_timestamps()
    RETURNS TRIGGER AS
'
    BEGIN
        IF (TG_OP = ''INSERT'') THEN
        NEW.createdAt := CURRENT_TIMESTAMP;
        NEW.updatedAt := CURRENT_TIMESTAMP;
    ELSIF (TG_OP = ''UPDATE'') THEN
        NEW.updatedAt := CURRENT_TIMESTAMP;
    END IF;
    RETURN NEW;
END;
' LANGUAGE plpgsql;
