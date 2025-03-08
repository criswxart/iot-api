
-- Crear la función del trigger
CREATE OR REPLACE FUNCTION before_insert_sensor_data()
RETURNS TRIGGER AS $$
DECLARE
    max_correlativo INT;
BEGIN
    SELECT COALESCE(MAX(sensor_correlative), 0) + 1
    INTO max_correlativo
    FROM sensor_data
    WHERE sensor_api_key = NEW.sensor_api_key;

    NEW.sensor_correlative := max_correlativo;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Crear el trigger
CREATE TRIGGER before_insert_sensor_data
BEFORE INSERT ON sensor_data
FOR EACH ROW
EXECUTE FUNCTION before_insert_sensor_data();


