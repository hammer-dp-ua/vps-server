Ext.define('VPSServer.model.LoginModel', {
   extend: 'Ext.data.Model',

   fields: [
      {name: 'id',   type: 'string'},
      {name: 'pass', type: 'string'}
   ],

   proxy: {
      type: 'rest',
      url : '/video/rest/login'
   }
});
