const express = require('express');
const router = express.Router();
const Order = require('../models/Order');
const { protect, adminOnly } = require('../middleware/auth');
const { sendOrderConfirmation } = require('../utils/emailService');

// Create order
router.post('/', async (req, res) => {
  try {
    const { userId, items, totalPrice, shippingAddress, contactNumber, email } = req.body;
    
    const order = await Order.create({
      userId,
      items,
      totalPrice,
      shippingAddress,
      contactNumber,
      status: 'pending',
      paymentStatus: 'pending'
    });

    // Send confirmation email
    if (email) {
      await sendOrderConfirmation(email, order._id, items, totalPrice);
    }

    res.status(201).json({
      success: true,
      orderId: order._id,
      message: 'Order placed successfully'
    });
  } catch (error) {
    res.status(400).json({ success: false, error: error.message });
  }
});

// Get user orders
router.get('/user/:userId', async (req, res) => {
  try {
    const orders = await Order.find({ userId: req.params.userId });
    res.json({ success: true, orders });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

// Get all orders (Admin)
router.get('/', protect, adminOnly, async (req, res) => {
  try {
    const orders = await Order.find();
    res.json({ success: true, total: orders.length, orders });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

module.exports = router;
