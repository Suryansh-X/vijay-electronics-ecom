// Run this script in MongoDB to initialize the database with sample products
// Command: mongosh < init-script.js or copy-paste in MongoDB compass

use('vijay-electronics');

// Create collections
db.createCollection('products');
db.createCollection('admins');
db.createCollection('categories');
db.createCollection('orders');

// Insert Categories
db.categories.insertMany([
  { name: 'TVs', icon: '📺', description: 'Wide range of televisions' },
  { name: 'Washing Machine', icon: '🧺', description: 'Automatic and semi-automatic washing machines' },
  { name: 'Geyser', icon: '💧', description: 'Electric water heaters' },
  { name: 'AC', icon: '❄️', description: 'Air conditioning units' },
  { name: 'Cooler', icon: '🌬️', description: 'Desert and personal coolers' },
  { name: 'Oven', icon: '🍳', description: 'Microwave and baking ovens' },
  { name: 'Other Devices', icon: '⚙️', description: 'Miscellaneous home appliances' },
  { name: 'Music', icon: '🎵', description: 'Audio systems and speakers' }
]);

// Insert Sample Products
db.products.insertMany([
  {
    name: 'Samsung 55 inch 4K Smart TV',
    category: 'TVs',
    price: 45000,
    originalPrice: 55000,
    discount: 18,
    quantity: 15,
    photo: 'https://via.placeholder.com/300x300?text=Samsung+55%22+4K+TV',
    description: 'Premium 55-inch 4K Smart TV with HDR support',
    specs: {
      size: '55 inch',
      resolution: '4K (3840x2160)',
      brand: 'Samsung',
      warranty: '2 years',
      refreshRate: '60Hz'
    },
    isFeatured: true,
    isActive: true,
    rating: 4.5,
    reviews: 128,
    createdAt: new Date(),
    updatedAt: new Date()
  },
  {
    name: 'LG Fully Automatic Washing Machine',
    category: 'Washing Machine',
    price: 28000,
    originalPrice: 35000,
    discount: 20,
    quantity: 8,
    photo: 'https://via.placeholder.com/300x300?text=LG+Washing+Machine',
    description: '7kg fully automatic front load washing machine',
    specs: {
      capacity: '7kg',
      type: 'Front Load',
      energy: '5 Star',
      warranty: '10 years drum, 5 years motor',
      brand: 'LG'
    },
    isFeatured: true,
    isActive: true,
    rating: 4.7,
    reviews: 95,
    createdAt: new Date(),
    updatedAt: new Date()
  },
  {
    name: 'Bajaj 25L Instant Water Heater',
    category: 'Geyser',
    price: 6500,
    originalPrice: 8000,
    discount: 19,
    quantity: 25,
    photo: 'https://via.placeholder.com/300x300?text=Bajaj+Geyser',
    description: '25 litre instant electric water heater',
    specs: {
      capacity: '25L',
      type: 'Instant',
      power: '4000W',
      warranty: '2 years',
      brand: 'Bajaj'
    },
    isFeatured: true,
    isActive: true,
    rating: 4.3,
    reviews: 156,
    createdAt: new Date(),
    updatedAt: new Date()
  },
  {
    name: 'Daikin 1.5 Ton Split AC',
    category: 'AC',
    price: 32000,
    originalPrice: 42000,
    discount: 24,
    quantity: 5,
    photo: 'https://via.placeholder.com/300x300?text=Daikin+AC',
    description: '1.5 Ton split air conditioner with inverter technology',
    specs: {
      capacity: '1.5 Ton',
      type: 'Split',
      inverter: 'Yes',
      energy: '5 Star',
      warranty: '5 years compressor'
    },
    isFeatured: true,
    isActive: true,
    rating: 4.6,
    reviews: 87,
    createdAt: new Date(),
    updatedAt: new Date()
  },
  {
    name: 'Havells Desert Air Cooler',
    category: 'Cooler',
    price: 8500,
    originalPrice: 10500,
    discount: 19,
    quantity: 12,
    photo: 'https://via.placeholder.com/300x300?text=Havells+Cooler',
    description: '65L desert cooler for large rooms',
    specs: {
      capacity: '65L',
      type: 'Desert',
      airflow: '1900 CMM',
      warranty: '1 year',
      brand: 'Havells'
    },
    isFeatured: false,
    isActive: true,
    rating: 4.2,
    reviews: 64,
    createdAt: new Date(),
    updatedAt: new Date()
  },
  {
    name: 'IFB Microwave Oven 25L',
    category: 'Oven',
    price: 9500,
    originalPrice: 12000,
    discount: 21,
    quantity: 10,
    photo: 'https://via.placeholder.com/300x300?text=IFB+Microwave',
    description: '25L convection microwave with 40 auto cook menus',
    specs: {
      capacity: '25L',
      power: '900W',
      warranty: '1 year',
      autoMenus: '40',
      brand: 'IFB'
    },
    isFeatured: false,
    isActive: true,
    rating: 4.4,
    reviews: 78,
    createdAt: new Date(),
    updatedAt: new Date()
  },
  {
    name: 'Sony Bluetooth Speaker',
    category: 'Music',
    price: 12000,
    originalPrice: 15000,
    discount: 20,
    quantity: 20,
    photo: 'https://via.placeholder.com/300x300?text=Sony+Speaker',
    description: 'Portable wireless Bluetooth speaker with 360 sound',
    specs: {
      power: '20W',
      battery: '12 hours',
      wireless: 'Bluetooth 5.0',
      warranty: '1 year',
      brand: 'Sony'
    },
    isFeatured: true,
    isActive: true,
    rating: 4.5,
    reviews: 142,
    createdAt: new Date(),
    updatedAt: new Date()
  },
  {
    name: 'Philips Vacuum Cleaner',
    category: 'Other Devices',
    price: 15000,
    originalPrice: 18500,
    discount: 19,
    quantity: 6,
    photo: 'https://via.placeholder.com/300x300?text=Philips+Vacuum',
    description: 'Powerful 800W dry vacuum cleaner',
    specs: {
      power: '800W',
      dustCapacity: '2L',
      warranty: '2 years',
      brand: 'Philips',
      type: 'Dry Vacuum'
    },
    isFeatured: false,
    isActive: true,
    rating: 4.3,
    reviews: 45,
    createdAt: new Date(),
    updatedAt: new Date()
  }
]);

// Create Admin User (bcrypt hash of 'Admin@123')
db.admins.insertOne({
  name: 'Admin',
  email: 'admin@vijayelectronics.com',
  password: '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeJlJ.aOcjf3dyJkN1m', // Hashed 'Admin@123'
  role: 'admin',
  permissions: {
    canAddProducts: true,
    canEditProducts: true,
    canDeleteProducts: true,
    canViewOrders: true,
    canViewStats: true
  },
  isActive: true,
  lastLogin: null,
  createdAt: new Date()
});

print('✅ Database initialized successfully!');
print('📊 Created 8 categories');
print('📦 Created 8 sample products');
print('👨‍💼 Created admin user');
print('\n🔐 Admin Credentials:');
print('Email: admin@vijayelectronics.com');
print('Password: Admin@123');
