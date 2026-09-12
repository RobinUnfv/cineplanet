// ============================================================
// Script de Inicialización - dbPartner
// MongoDB 7.0 | Docker
// Colección: socio
// ============================================================

// Autenticarse como root para crear el usuario de la BD
db = db.getSiblingDB('admin');

db.createUser({
  user: 'partner_user',
  pwd: 'partner2024',
  roles: [
    {
      role: 'readWrite',
      db: 'dbPartner'
    }
  ]
});

// Cambiar a la base de datos dbPartner
db = db.getSiblingDB('dbPartner');

// ============================================================
// CREAR COLECCIÓN con validación de esquema
// ============================================================
db.createCollection('socio', {
  validator: {
    $jsonSchema: {
      bsonType: 'object',
      required: ['dni', 'nombre', 'paterno', 'materno', 'correo', 'telefono', 'sts_socio', 'tipo_membresia'],
      properties: {
        dni: {
          bsonType: 'string',
          minLength: 8,
          maxLength: 8,
          description: 'DNI debe ser string de 8 dígitos - obligatorio'
        },
        nombre: {
          bsonType: 'string',
          minLength: 1,
          description: 'Nombre es obligatorio'
        },
        paterno: {
          bsonType: 'string',
          minLength: 1,
          description: 'Apellido paterno es obligatorio'
        },
        materno: {
          bsonType: 'string',
          minLength: 1,
          description: 'Apellido materno es obligatorio'
        },
        correo: {
          bsonType: 'string',
          pattern: '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$',
          description: 'Correo electrónico válido - obligatorio'
        },
        telefono: {
          bsonType: 'string',
          minLength: 9,
          maxLength: 12,
          description: 'Teléfono es obligatorio'
        },
        sts_socio: {
          bsonType: 'string',
          enum: ['ACTIVO', 'INACTIVO'],
          description: 'Estado del socio: ACTIVO o INACTIVO - obligatorio'
        },
        tipo_membresia: {
          bsonType: 'string',
          enum: ['GOLD', 'PLATINUM'],
          description: 'Tipo de membresía: GOLD o PLATINUM - obligatorio'
        }
      }
    }
  },
  validationLevel: 'strict',
  validationAction: 'error'
});

print('✅ Colección "socio" creada con validación de esquema.');

// ============================================================
// CREAR ÍNDICES
// ============================================================
db.socio.createIndex({ dni: 1 }, { unique: true, name: 'idx_dni_unique' });
db.socio.createIndex({ correo: 1 }, { unique: true, name: 'idx_correo_unique' });
db.socio.createIndex({ sts_socio: 1 }, { name: 'idx_sts_socio' });
db.socio.createIndex({ tipo_membresia: 1 }, { name: 'idx_tipo_membresia' });
db.socio.createIndex({ paterno: 1, materno: 1, nombre: 1 }, { name: 'idx_apellidos_nombre' });

print('✅ Índices creados correctamente.');

// ============================================================
// INSERTAR 20 SOCIOS
// ============================================================
var socios = [
  {
    dni: '71234501',
    nombre: 'Carlos Andres',
    paterno: 'Quispe',
    materno: 'Mamani',
    correo: 'carlos.quispe@gmail.com',
    telefono: '987654321',
    sts_socio: 'ACTIVO',
    tipo_membresia: 'GOLD',
    fecha_registro: new Date('2022-03-15')
  },
  {
    dni: '72345602',
    nombre: 'Maria Elena',
    paterno: 'Flores',
    materno: 'Huanca',
    correo: 'maria.flores@hotmail.com',
    telefono: '976543210',
    sts_socio: 'ACTIVO',
    tipo_membresia: 'PLATINUM',
    fecha_registro: new Date('2021-08-22')
  },
  {
    dni: '73456703',
    nombre: 'Luis Miguel',
    paterno: 'Rojas',
    materno: 'Vargas',
    correo: 'luis.rojas@yahoo.com',
    telefono: '965432109',
    sts_socio: 'INACTIVO',
    tipo_membresia: 'GOLD',
    fecha_registro: new Date('2020-11-05')
  },
  {
    dni: '74567804',
    nombre: 'Ana Lucia',
    paterno: 'Torres',
    materno: 'Castillo',
    correo: 'ana.torres@gmail.com',
    telefono: '954321098',
    sts_socio: 'ACTIVO',
    tipo_membresia: 'PLATINUM',
    fecha_registro: new Date('2023-01-10')
  },
  {
    dni: '75678905',
    nombre: 'Jorge Eduardo',
    paterno: 'Mendoza',
    materno: 'Rios',
    correo: 'jorge.mendoza@gmail.com',
    telefono: '943210987',
    sts_socio: 'ACTIVO',
    tipo_membresia: 'GOLD',
    fecha_registro: new Date('2022-07-18')
  },
  {
    dni: '76789006',
    nombre: 'Patricia Susana',
    paterno: 'Garcia',
    materno: 'Lozano',
    correo: 'patricia.garcia@outlook.com',
    telefono: '932109876',
    sts_socio: 'INACTIVO',
    tipo_membresia: 'PLATINUM',
    fecha_registro: new Date('2019-05-30')
  },
  {
    dni: '77890107',
    nombre: 'Roberto Carlos',
    paterno: 'Sanchez',
    materno: 'Pineda',
    correo: 'roberto.sanchez@gmail.com',
    telefono: '921098765',
    sts_socio: 'ACTIVO',
    tipo_membresia: 'GOLD',
    fecha_registro: new Date('2023-04-25')
  },
  {
    dni: '78901208',
    nombre: 'Diana Carolina',
    paterno: 'Lopez',
    materno: 'Tapia',
    correo: 'diana.lopez@hotmail.com',
    telefono: '910987654',
    sts_socio: 'ACTIVO',
    tipo_membresia: 'PLATINUM',
    fecha_registro: new Date('2022-12-01')
  },
  {
    dni: '79012309',
    nombre: 'Fernando Jose',
    paterno: 'Ramirez',
    materno: 'Chavez',
    correo: 'fernando.ramirez@gmail.com',
    telefono: '909876543',
    sts_socio: 'INACTIVO',
    tipo_membresia: 'GOLD',
    fecha_registro: new Date('2021-02-14')
  },
  {
    dni: '70123410',
    nombre: 'Claudia Beatriz',
    paterno: 'Herrera',
    materno: 'Salinas',
    correo: 'claudia.herrera@yahoo.com',
    telefono: '998765432',
    sts_socio: 'ACTIVO',
    tipo_membresia: 'PLATINUM',
    fecha_registro: new Date('2023-06-08')
  },
  {
    dni: '71234511',
    nombre: 'Miguel Angel',
    paterno: 'Paredes',
    materno: 'Fuentes',
    correo: 'miguel.paredes@gmail.com',
    telefono: '987654320',
    sts_socio: 'ACTIVO',
    tipo_membresia: 'GOLD',
    fecha_registro: new Date('2022-09-19')
  },
  {
    dni: '72345612',
    nombre: 'Sandra Paola',
    paterno: 'Cruz',
    materno: 'Medina',
    correo: 'sandra.cruz@outlook.com',
    telefono: '976543219',
    sts_socio: 'ACTIVO',
    tipo_membresia: 'PLATINUM',
    fecha_registro: new Date('2021-11-28')
  },
  {
    dni: '73456713',
    nombre: 'Alejandro David',
    paterno: 'Vega',
    materno: 'Moreno',
    correo: 'alejandro.vega@gmail.com',
    telefono: '965432108',
    sts_socio: 'INACTIVO',
    tipo_membresia: 'GOLD',
    fecha_registro: new Date('2020-04-07')
  },
  {
    dni: '74567814',
    nombre: 'Gabriela Vanesa',
    paterno: 'Reyes',
    materno: 'Ortega',
    correo: 'gabriela.reyes@hotmail.com',
    telefono: '954321097',
    sts_socio: 'ACTIVO',
    tipo_membresia: 'PLATINUM',
    fecha_registro: new Date('2023-02-17')
  },
  {
    dni: '75678915',
    nombre: 'Raul Antonio',
    paterno: 'Jimenez',
    materno: 'Palacios',
    correo: 'raul.jimenez@gmail.com',
    telefono: '943210986',
    sts_socio: 'ACTIVO',
    tipo_membresia: 'GOLD',
    fecha_registro: new Date('2022-05-03')
  },
  {
    dni: '76789016',
    nombre: 'Veronica Isabel',
    paterno: 'Alvarado',
    materno: 'Campos',
    correo: 'veronica.alvarado@yahoo.com',
    telefono: '932109875',
    sts_socio: 'INACTIVO',
    tipo_membresia: 'PLATINUM',
    fecha_registro: new Date('2019-10-21')
  },
  {
    dni: '77890117',
    nombre: 'Oscar Ivan',
    paterno: 'Navarro',
    materno: 'Espinoza',
    correo: 'oscar.navarro@gmail.com',
    telefono: '921098764',
    sts_socio: 'ACTIVO',
    tipo_membresia: 'GOLD',
    fecha_registro: new Date('2023-08-12')
  },
  {
    dni: '78901218',
    nombre: 'Luciana Fernanda',
    paterno: 'Delgado',
    materno: 'Montes',
    correo: 'luciana.delgado@outlook.com',
    telefono: '910987653',
    sts_socio: 'ACTIVO',
    tipo_membresia: 'PLATINUM',
    fecha_registro: new Date('2022-10-30')
  },
  {
    dni: '79012319',
    nombre: 'Hector Manuel',
    paterno: 'Cardenas',
    materno: 'Serrano',
    correo: 'hector.cardenas@gmail.com',
    telefono: '909876542',
    sts_socio: 'INACTIVO',
    tipo_membresia: 'GOLD',
    fecha_registro: new Date('2021-06-09')
  },
  {
    dni: '70123420',
    nombre: 'Melissa Roxana',
    paterno: 'Gutierrez',
    materno: 'Aguilar',
    correo: 'melissa.gutierrez@hotmail.com',
    telefono: '998765431',
    sts_socio: 'ACTIVO',
    tipo_membresia: 'PLATINUM',
    fecha_registro: new Date('2023-09-05')
  }
];

var resultado = db.socio.insertMany(socios);
print('Se insertaron ' + resultado.insertedIds.length + ' socios correctamente.');

// ============================================================
// VERIFICACIÓN FINAL
// ============================================================
print('\n RESUMEN DE DATOS CARGADOS:');
print('   Total socios       : ' + db.socio.countDocuments());
print('   Socios ACTIVOs     : ' + db.socio.countDocuments({ sts_socio: 'ACTIVO' }));
print('   Socios INACTIVOS   : ' + db.socio.countDocuments({ sts_socio: 'INACTIVO' }));
print('   Membresía GOLD     : ' + db.socio.countDocuments({ tipo_membresia: 'GOLD' }));
print('   Membresía PLATINUM : ' + db.socio.countDocuments({ tipo_membresia: 'PLATINUM' }));
print('\n Base de datos dbPartner inicializada correctamente.\n');
