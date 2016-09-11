Ext.define('VPSServer.model.FileModel', {
   extend: 'Ext.data.Model',

   fields: [
      {name: 'name', type: 'string'},
      {
         name: 'creationDate',
         type: 'date',
         /**
          * d - Day of the month, 2 digits with leading zeros
          * m - Numeric representation of a month, with leading zeros
          * Y - A full numeric representation of a year, 4 digits
          * H - 24-hour format of an hour with leading zeros
          * i - Minutes, with leading zeros
          */
         dateFormat: 'd-m-Y H:i:s'
      },
      {
         name: 'size',
         type: 'float',
         sortType: 'asFloat',
         convert: function (val) {
            if (val) {
               return parseFloat(val / 1024 / 1024).toFixed(2);
            } else {
               return 0;
            }
         }
      }
   ],
   idProperty : 'name'
});
