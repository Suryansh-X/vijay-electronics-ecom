# WhatsApp Integration Guide

## Overview

The WhatsApp chat integration allows customers to quickly reach out to Vijay Electronics with pre-filled messages about products.

## Features

✅ **Quick Chat Widget** - Bottom right corner of website
✅ **Pre-filled Messages** - "Hi, I found this product on your website"
✅ **Direct WhatsApp Link** - Opens WhatsApp Web or mobile app
✅ **Multiple Contact Options** - Quick messages for common questions
✅ **Mobile Optimized** - Works seamlessly on all devices

## Implementation

### 1. Frontend Integration

The WhatsApp chat component is already integrated in `frontend/src/components/WhatsAppChat.jsx`

**How to use in your pages:**

```jsx
import WhatsAppChat from './components/WhatsAppChat';

function MyPage() {
  return (
    <div>
      {/* Your page content */}
      <WhatsAppChat />
    </div>
  );
}
```

### 2. Configuration

Update the phone number in `frontend/src/components/WhatsAppChat.jsx`:

```javascript
const phoneNumber = '919876898832'; // Replace with your WhatsApp number (without + sign)
```

**Format:**
- Country code: 91 (India)
- Phone: 9876898832
- Full: 919876898832

### 3. WhatsApp Message Templates

The component includes quick message templates:

```javascript
const quickMessages = [
  'I found a Samsung TV on your site. Can you tell me more?',
  'What\'s the warranty on washing machines?',
  'Do you have AC in stock?',
  'Can I get a discount?',
  'What\'s the delivery time?'
];
```

Customize these messages based on your needs.

## How It Works

### User Flow:

1. User clicks the **Chat** button in bottom-right corner
2. Chat widget opens with quick message options
3. User selects a message or types custom message
4. Clicking "Send on WhatsApp" opens WhatsApp
5. Message is pre-filled and ready to send

### Message Format:

Default message:
```
Hi, I found [product] on your website. Can you tell me more about this?
```

This message is automatically encoded and sent to the phone number.

## Customization

### Change Shop Name

```javascript
const shopName = 'Vijay Electronics';
```

### Change Colors

Update the color classes in the component:
- `bg-green-500` - Main color (green for WhatsApp)
- `bg-green-600` - Hover color

### Add More Quick Messages

```javascript
const quickMessages = [
  'Your message here',
  'Another message',
  // Add more...
];
```

### Dynamic Product Messages

Pass product info to the component:

```jsx
<WhatsAppChat product={productName} />
```

Then update the component to use it:

```javascript
const fullMessage = `Hi, I found "${props.product}" on your website. Can you tell me more about this?`;
```

## Testing

### Test Links

1. **Web**: https://web.whatsapp.com/
2. **Mobile**: WhatsApp app

### Test Message

Click a quick message to send a test. You'll be redirected to WhatsApp with the pre-filled message.

## Troubleshooting

### Issue: Chat widget not appearing
- Check if component is imported in the page
- Verify z-index is not blocked by other elements

### Issue: WhatsApp link not opening
- Verify phone number format (should be 91XXXXXXXXXX)
- Check if WhatsApp is installed on device
- Try using `https://wa.me/` link directly

### Issue: Special characters in message
- The component uses `encodeURIComponent()` to handle special characters
- Test with emoji and special characters

## WhatsApp Business API (Optional)

For advanced features, integrate WhatsApp Business API:

1. Sign up at https://www.whatsapp.com/business/
2. Get your Business Account ID
3. Generate API credentials
4. Set up message templates
5. Integrate with backend

## Backend Integration

To track WhatsApp interactions, add to backend:

```javascript
// routes/whatsapp.js
router.post('/log-chat', async (req, res) => {
  try {
    const { userId, message, productId } = req.body;
    // Log interaction
    await WhatsAppInteraction.create({
      userId,
      message,
      productId,
      timestamp: new Date()
    });
    res.json({ success: true });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});
```

## Statistics

Track WhatsApp engagement:
- Total chat initiations
- Most asked questions
- Conversion rate
- Response time

## Support

**Vijay Electronics**
- Phone: +91 9876898832
- Email: navshop07@gmail.com

© 2026 Vijay Electronics. All rights reserved.
