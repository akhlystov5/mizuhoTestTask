--add a record older than 30 days to demonstrate it is cleaned up properly
INSERT INTO PRICE (id, created, instrument, isin, price, vendor) VALUES
  (250123, parsedatetime('17-09-2012 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), 'Old Instrument', 'US1234567899', 99.00, 'AncientVendor');