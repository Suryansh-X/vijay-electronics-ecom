# Email Notifications Guide

## Overview

The email notification system sends automated emails for orders, product updates, and admin notifications.

## Features

✅ **Order Confirmation Emails** - Sent immediately after order placement
✅ **Rich HTML Templates** - Professional email design
✅ **Product Update Notifications** - Admin notifications
✅ **Automated Delivery** - No manual intervention needed
✅ **Customizable Templates** - Easy to modify content

## Setup

### 1. Gmail Configuration

#### Enable 2-Step Verification
1. Go to https://myaccount.google.com/security
2. Enable "2-Step Verification"
3. Verify your phone number

#### Generate App Password
1. Go to https://myaccount.google.com/apppasswords
2. Select "Mail" and "Windows Computer"
3. Copy the generated password

#### Update .env file

```bash
# Email Configuration
SMTP_HOST=smtp.gmail.com
SMTP_PORT=587
SMTP_USER=your-email@gmail.com
SMTP_PASS=your-app-password
```

### 2. Install Email Package

```bash
cd backend
npm install nodemailer
```

### 3. Test Email Service

```bash
node
> const emailService = require('./utils/emailService');
> emailService.sendOrderConfirmation('test@email.com', 'ORDER123', [{ productName: 'TV', quantity: 1, price: 45000 }], 45000);
```

## Email Templates

### 1. Order Confirmation Email

**Trigger**: When customer places order

**Content**:
- Order ID
- Product details (name, quantity, price)
- Total amount
- Shipping address
- Contact information

**Customization**:

Edit in `backend/utils/emailService.js`:

```javascript
const htmlContent = `
  <!-- Modify HTML here -->
`;
```

### 2. Admin Update Notification

**Trigger**: When admin updates product

**Content**:
- Product name
- Update type (price, inventory, photo, specs)
- Timestamp

## API Integration

### Send Order Confirmation

```javascript
const { sendOrderConfirmation } = require('../utils/emailService');

// In your order creation endpoint
await sendOrderConfirmation(
  email,           // Customer email
  orderId,         // Order ID
  items,          // Product items array
  totalPrice      // Total amount
);
```

### Send Product Update Notification

```javascript
const { sendProductUpdateNotification } = require('../utils/emailService');

// When product is updated
await sendProductUpdateNotification(
  adminEmail,     // Admin email
  productName,    // Product name
  'Price Updated' // Update type
);
```

## Customization

### Change Email Subject

```javascript
const mailOptions = {
  subject: `Custom Subject - Order #${orderId}`
};
```

### Change Email Styling

Modify CSS in `emailService.js`:

```javascript
const htmlContent = `
  <style>
    body { background-color: #ffffff; } /* Change background */
    .header { background: #667eea; }     /* Change header color */
  </style>
`;
```

### Add Company Logo

```html
<img src="https://your-domain.com/logo.png" alt="Logo" width="200" />
```

### Customize Shop Information

Update in template:

```html
<p>📞 Phone: +91 9876898832</p>
<p>📧 Email: navshop07@gmail.com</p>
<p>⏰ Hours: 9 AM - 9 PM</p>
```

## Testing

### Test Order Email

```javascript
// In a test file or MongoDB shell
const emailService = require('./utils/emailService');

await emailService.sendOrderConfirmation(
  'your-email@gmail.com',
  'TEST-ORDER-001',
  [
    { productName: 'Samsung TV', quantity: 1, price: 45000 },
    { productName: 'AC', quantity: 1, price: 32000 }
  ],
  77000
);
```

### Test Admin Notification

```javascript
await emailService.sendProductUpdateNotification(
  'admin@vijayelectronics.com',
  'Samsung 55" TV',
  'Price Updated to ₹42000'
);
```

## Troubleshooting

### Issue: "Invalid login credentials"

- Verify Gmail password is correct
- Generate new App Password
- Enable "Less secure apps" (not recommended)

### Issue: "SMTP connection error"

- Check internet connection
- Verify SMTP settings in .env
- Try different email provider

### Issue: "Email not received"

- Check spam folder
- Verify recipient email address
- Check email logs
- Try resending

## Alternative Email Providers

### SendGrid

```javascript
const sgMail = require('@sendgrid/mail');
sgMail.setApiKey(process.env.SENDGRID_API_KEY);
```

### Mailgun

```javascript
const mailgun = require('mailgun-js')(
  { apiKey: process.env.MAILGUN_API_KEY }
);
```

### AWS SES

```javascript
const AWS = require('aws-sdk');
const ses = new AWS.SES({ region: 'us-east-1' });
```

## Email Lists

### Implement Email Subscription

```javascript
// routes/newsletter.js
router.post('/subscribe', async (req, res) => {
  const { email } = req.body;
  await Newsletter.create({ email });
  res.json({ success: true });
});
```

## Analytics

Track email metrics:
- Emails sent
- Emails delivered
- Open rate
- Click-through rate

## Compliance

✅ **GDPR**: Include unsubscribe link
✅ **CAN-SPAM**: Include business address
✅ **Authentication**: Set up SPF, DKIM, DMARC

## Support

**Vijay Electronics**
- Phone: +91 9876898832
- Email: navshop07@gmail.com

© 2026 Vijay Electronics. All rights reserved.
